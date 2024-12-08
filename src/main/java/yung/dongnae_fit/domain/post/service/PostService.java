package yung.dongnae_fit.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.member.repository.MemberRepository;
import yung.dongnae_fit.domain.post.dto.PostContentResponseDTO;
import yung.dongnae_fit.domain.post.dto.PostListResponseDTO;
import yung.dongnae_fit.domain.post.dto.PostRequestDTO;
import yung.dongnae_fit.domain.post.dto.PostResponseDTO;
import yung.dongnae_fit.domain.post.entity.Post;
import yung.dongnae_fit.domain.post.repository.PostRepository;
import yung.dongnae_fit.domain.postComment.dto.CommentDataDTO;
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
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final RequestScopedStorage requestScopedStorage;
    private final S3Uploader s3Uploader;


    @Transactional
    public PostResponseDTO postCotent(PostRequestDTO postRequestDTO) {
        String kakaoId = requestScopedStorage.getKakaoId();
        Optional<Member> member = memberRepository.findByKakaoId(kakaoId);

        if (member.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId);
        }

        Member postMember = member.get();

        Post post = Post.builder()
                .title(postRequestDTO.getPostTitle())
                .detail(postRequestDTO.getPostDetail())
                .member(postMember)
                .build();
        postRepository.save(post);

        return new PostResponseDTO(post.getId(), post.getTitle(), post.getDetail(), null);
    }

    @Transactional
    public PostResponseDTO uploadPostImage(Long postId, MultipartFile multipartFile) throws IOException {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId).orElse(null);
        Post post = postRepository.findByIdAndMember(postId, member).orElse(null);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
        } else {
            String url = s3Uploader.upload(multipartFile, "post");
            post.setImage(url);
            postRepository.save(post);
        }
        return new PostResponseDTO(post.getId(), post.getTitle(), post.getDetail(), post.getImage());
    }

    public List<PostListResponseDTO> getPostList(String search) {
        List<PostListResponseDTO> postListResponseDTOList = new ArrayList<>();
        List<Post> postList;

        if (search == null) {
            postList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
        } else {
            postList = postRepository.findByTitleOrDetailLike(search);
        }
        for (Post post : postList) {
            PostListResponseDTO postListResponseDTO = new PostListResponseDTO(post);
            postListResponseDTOList.add(postListResponseDTO);
        }
        return postListResponseDTOList;
    }

    @Transactional
    public void deletePost(Long postId) throws IOException {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId).orElse(null);
        Post post = postRepository.findByIdAndMember(postId, member).orElse(null);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
        } else {
            s3Uploader.delete(post.getImage());
            postRepository.deleteByIdAndMember(postId, member);
        }
    }


    @Transactional
    public PostContentResponseDTO getCotent(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));
        boolean postLikeStatus = false;
        boolean postSaveStatus = false;

        if (requestScopedStorage.getKakaoId() != null) {
            String kakaoId = requestScopedStorage.getKakaoId();
            Member member = memberRepository.findByKakaoId(kakaoId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));

            postLikeStatus = post.getPostLikes().stream()
                    .anyMatch(like -> like.getMember().equals(member));
            postSaveStatus = post.getPostSaves().stream()
                    .anyMatch(save -> save.getMember().equals(member));
        }

        List<CommentDataDTO> commentDataDTOs = post.getPostComment().stream()
                .map(CommentDataDTO::new)
                .collect(Collectors.toList());

        return PostContentResponseDTO.builder()
                .post(post)
                .postLikeStatus(postLikeStatus)
                .postSaveStatus(postSaveStatus)
                .comments(commentDataDTOs)
                .build();
    }
}
