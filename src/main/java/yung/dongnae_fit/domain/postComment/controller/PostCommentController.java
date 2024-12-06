package yung.dongnae_fit.domain.postComment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yung.dongnae_fit.domain.postComment.dto.CommentRequestDTO;
import yung.dongnae_fit.domain.postComment.service.PostCommentService;
import yung.dongnae_fit.global.dto.ResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostCommentController {

    private final PostCommentService postCommentService;

    @PostMapping("/{postId}/comment")
    public ResponseEntity<?> createComment(@PathVariable("postId") Long postId,
                                           @RequestBody CommentRequestDTO commentRequestDTO) {
        postCommentService.createComment(postId, commentRequestDTO.getCommentDetail());
        ResponseDTO<?> responseDTO = ResponseDTO.created("작성되었습니다.");
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("postId") Long postId,
                                           @PathVariable("commentId") Long commentId) {
        postCommentService.deleteComment(postId, commentId);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("삭제되었습니다.");
        return ResponseEntity.ok(responseDTO);
    }

}
