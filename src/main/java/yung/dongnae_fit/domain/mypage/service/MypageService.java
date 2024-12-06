package yung.dongnae_fit.domain.mypage.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.member.repository.MemberRepository;
import yung.dongnae_fit.domain.mypage.dto.MypageGetResponseDTO;
import yung.dongnae_fit.domain.mypage.dto.MypageRegionDTO;
import yung.dongnae_fit.domain.post.dto.PostListResponseDTO;
import yung.dongnae_fit.domain.post.dto.PostResponseDTO;
import yung.dongnae_fit.domain.post.entity.Post;
import yung.dongnae_fit.domain.post.repository.PostRepository;
import yung.dongnae_fit.domain.postComment.entity.PostComment;
import yung.dongnae_fit.domain.postComment.repository.PostCommentRepository;
import yung.dongnae_fit.domain.postSave.entity.PostSave;
import yung.dongnae_fit.domain.postSave.repository.PostSaveRepository;
import yung.dongnae_fit.domain.program.dto.ProgramDataDTO;
import yung.dongnae_fit.domain.program.entity.Program;
import yung.dongnae_fit.domain.programFacility.entity.ProgramFacility;
import yung.dongnae_fit.domain.programSave.entity.ProgramSave;
import yung.dongnae_fit.domain.programSave.repository.ProgramSaveRepository;
import yung.dongnae_fit.domain.review.entity.Review;
import yung.dongnae_fit.domain.review.repository.ReviewRepository;
import yung.dongnae_fit.global.CalculateDistance;
import yung.dongnae_fit.global.RequestScopedStorage;
import yung.dongnae_fit.global.service.S3Uploader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@Service
public class MypageService {
    private final MemberRepository memberRepository;
    private final RequestScopedStorage requestScopedStorage;
    private final S3Uploader s3Uploader;
    private final ProgramSaveRepository programSaveRepository;
    private final CalculateDistance calculateDistance;
    private final ReviewRepository reviewRepository;
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final PostSaveRepository postSaveRepository;

    @Transactional
    public MypageGetResponseDTO getMember() {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId).orElse(null);
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId);
        }
        return new MypageGetResponseDTO(
                member.getName(),
                member.getRegion(),
                member.getProfile());
    }

    @Transactional
    public void updateRegion(MypageRegionDTO mypageRegionDTO) {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId).orElse(null);
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId);
        } else {
            member.setRegion(mypageRegionDTO.getRegion());
            member.setProvince(mypageRegionDTO.getProvince());
            member.setDistrict(mypageRegionDTO.getDistrict());
            member.setLatitude(mypageRegionDTO.getLatitude());
            member.setLongitude(mypageRegionDTO.getLongitude());
            memberRepository.save(member);
        }
    }

    @Transactional
    public void updateName(String name) {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId).orElse(null);
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId);
        } else {
            member.setName(name);
            memberRepository.save(member);
        }
    }

    @Transactional
    public void uploadProfile(MultipartFile multipartFile) throws IOException {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId).orElse(null);
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId);
        } else {
            String url = s3Uploader.upload(multipartFile, "profile");
            member.setProfile(url);
            memberRepository.save(member);
        }
    }

    @Transactional
    public List<ProgramDataDTO> getSavedPrograms() {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));

        List<ProgramSave> programSaves = programSaveRepository.findByMember(member);

        return programSaves.stream()
                .map(programSave -> {
                    Program program = programSave.getProgram();
                    ProgramFacility facility = program.getProgramFacility();

                    double km = calculateDistance.calculateDistance(
                            member.getLatitude(), member.getLongitude(), facility.getLatitude(), facility.getLongitude());

                    return ProgramDataDTO.builder()
                            .program(program)
                            .programFacility(facility)
                            .km(km) // km 값이 필요하면 추가 로직 작성
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProgramDataDTO> getReviewedPrograms() {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));

        List<Review> Reviews = reviewRepository.findByMember(member);

        return new ArrayList<>(Reviews.stream()
                .map(review -> {
                    Program program = review.getProgram();
                    ProgramFacility facility = program.getProgramFacility();

                    double km = calculateDistance.calculateDistance(
                            member.getLatitude(), member.getLongitude(), facility.getLatitude(), facility.getLongitude());

                    return ProgramDataDTO.builder()
                            .program(program)
                            .programFacility(facility)
                            .km(km)
                            .build();
                })
                .collect(Collectors.toMap(
                        ProgramDataDTO::getProgramId,
                        programDataDTO -> programDataDTO,
                        (existing, replacement) -> existing))
                .values());
    }

    @Transactional
    public List<PostListResponseDTO> getPostedPosts() {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));

        List<Post> posts = postRepository.findByMember(member);
        return posts.stream()
                .map(PostListResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostListResponseDTO> getCommentedPosts() {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));

        List<PostComment> postComments = postCommentRepository.findByMember(member);
        return new ArrayList<>(postComments.stream()
                .map(postComment -> new PostListResponseDTO(postComment.getPost()))
                .collect(Collectors.toMap(
                        PostListResponseDTO::getPostId,
                        postListResponseDTO -> postListResponseDTO,
                        (existing, replacement) -> existing))
                .values());
    }

    @Transactional
    public List<PostListResponseDTO> getSavedPosts() {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));

        List<PostSave> postSaves = postSaveRepository.findByMember(member);
        return postSaves.stream()
                .map(postSave -> new PostListResponseDTO(postSave.getPost()))
                .collect(Collectors.toList());
    }
}
