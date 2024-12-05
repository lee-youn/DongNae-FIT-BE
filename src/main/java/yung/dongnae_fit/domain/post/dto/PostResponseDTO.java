package yung.dongnae_fit.domain.post.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostResponseDTO {
    private Long postId;
    private String postTitle;
    private String postDetail;
    private String postImage;

    @Builder
    public PostResponseDTO(Long postId,String postTitle, String postDetail, String postImage) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postDetail = postDetail;
        this.postImage = postImage;
    }
}
