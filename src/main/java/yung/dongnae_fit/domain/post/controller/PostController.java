package yung.dongnae_fit.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yung.dongnae_fit.domain.post.dto.PostImageRequestDTO;
import yung.dongnae_fit.domain.post.dto.PostListResponseDTO;
import yung.dongnae_fit.domain.post.dto.PostRequestDTO;
import yung.dongnae_fit.domain.post.dto.PostResponseDTO;
import yung.dongnae_fit.domain.post.service.PostLikeService;
import yung.dongnae_fit.domain.post.service.PostSaveService;
import yung.dongnae_fit.domain.post.service.PostService;
import yung.dongnae_fit.global.dto.ResponseDTO;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;
    private final PostSaveService postSaveService;

    @PostMapping
    public ResponseEntity<?> postContent(@RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO postResponseDTO = postService.postCotent(postRequestDTO);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("작성되었습니다.", postResponseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/{postId}/image")
    public ResponseEntity<?> postImage(@PathVariable("postId") Long postId,
                                       @RequestBody PostImageRequestDTO postImageRequestDTO) throws IOException
    {
        PostResponseDTO postResponseDTO = postService.uploadPostImage(postId, postImageRequestDTO.getPostImage());
        ResponseDTO<?> responseDTO = ResponseDTO.ok("작성되었습니다.", postResponseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts(@RequestParam(required = false) String search) {
        List<PostListResponseDTO> postListResponseDTOList = postService.getPostList(search);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("커뮤니티 목록이 조회되었습니다.", postListResponseDTOList);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") Long postId) throws IOException {
        postService.deletePost(postId);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("해당 게시글이 삭제되었습니다.");
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable("postId") Long postId) {
        postLikeService.toggleLike(postId);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("요청이 성공하였습니다.");
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{postId}/save")
    public ResponseEntity<?> savePost(@PathVariable("postId") Long postId) {
        postSaveService.toggleSave(postId);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("요청이 성공하였습니다.");
        return ResponseEntity.ok(responseDTO);
    }
}
