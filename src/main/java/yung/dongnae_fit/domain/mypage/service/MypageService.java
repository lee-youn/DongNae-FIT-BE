package yung.dongnae_fit.domain.mypage.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.member.repository.MemberRepository;
import yung.dongnae_fit.domain.mypage.dto.MypageGetResponseDTO;
import yung.dongnae_fit.domain.mypage.dto.MypageRegionDTO;
import yung.dongnae_fit.global.RequestScopedStorage;
import yung.dongnae_fit.global.service.S3Uploader;

import java.io.IOException;

@RequiredArgsConstructor
@Log4j2
@Service
public class MypageService {
    private final MemberRepository memberRepository;
    private final RequestScopedStorage requestScopedStorage;
    private final S3Uploader s3Uploader;

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

}
