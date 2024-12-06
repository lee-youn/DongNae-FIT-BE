package yung.dongnae_fit.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yung.dongnae_fit.domain.facility.dto.FacilitiesResponseDTO;
import yung.dongnae_fit.domain.member.service.auth.JwtTokenProvider;
import yung.dongnae_fit.domain.post.dto.*;
import yung.dongnae_fit.domain.post.service.PostLikeService;
import yung.dongnae_fit.domain.post.service.PostSaveService;
import yung.dongnae_fit.domain.post.service.PostService;
import yung.dongnae_fit.global.RequestScopedStorage;
import yung.dongnae_fit.global.dto.ResponseDTO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;
    private final PostSaveService postSaveService;
    private final RequestScopedStorage requestScopedStorage;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/auth/posts")
    public ResponseEntity<?> postContent(@RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO postResponseDTO = postService.postCotent(postRequestDTO);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("작성되었습니다.", postResponseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/auth/posts/{postId}/image")
    public ResponseEntity<?> postImage(@PathVariable("postId") Long postId,
                                       @RequestBody PostImageRequestDTO postImageRequestDTO) throws IOException
    {
        PostResponseDTO postResponseDTO = postService.uploadPostImage(postId, postImageRequestDTO.getPostImage());
        ResponseDTO<?> responseDTO = ResponseDTO.ok("작성되었습니다.", postResponseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts(@RequestParam(required = false) String search) {
        List<PostListResponseDTO> postListResponseDTOList = postService.getPostList(search);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("커뮤니티 목록이 조회되었습니다.", postListResponseDTOList);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/auth/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") Long postId) throws IOException {
        postService.deletePost(postId);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("해당 게시글이 삭제되었습니다.");
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/auth/posts/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable("postId") Long postId) {
        postLikeService.toggleLike(postId);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("요청이 성공하였습니다.");
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/auth/posts/{postId}/save")
    public ResponseEntity<?> savePost(@PathVariable("postId") Long postId) {
        postSaveService.toggleSave(postId);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("요청이 성공하였습니다.");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> getPost(@PathVariable("postId") Long postId
            ,@RequestHeader(value = "Authorization", required = false) String token) {

        if (token != null) {
            String accessToken = token.substring(7);
            log.info("Access Token: " + accessToken);
            Map<String, Object> claims = jwtTokenProvider.validateToken(accessToken).getBody();
            log.info("Token validation completed. Claims: " + claims);

            String kakaoId = (String) claims.get("sub");
            requestScopedStorage.setKakaoId(kakaoId);
        }

        PostContentResponseDTO postContentResponseDTO = postService.getCotent(postId);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("해당 게시글 내용이 조회되었습니다.", postContentResponseDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
