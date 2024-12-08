package yung.dongnae_fit.domain.post.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yung.dongnae_fit.domain.post.entity.Post;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class PostListResponseDTO {
    private Long postId;
    private String postTitle;
    private String postDetail;
    private LocalDateTime postDate;
    private String postImage;
    private Long postLikeCount;
    private Long postSaveCount;

    @Builder
    public PostListResponseDTO(Post post) {
        this.postId = post.getId();
        this.postTitle = post.getTitle();
        this.postDetail = post.getDetail();
        this.postImage = post.getImage();
        this.postDate = post.getDate();
        this.postLikeCount = (long) post.getPostLikes().size();
        this.postSaveCount = (long) post.getPostSaves().size();
    }
}
