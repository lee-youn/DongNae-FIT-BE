package yung.dongnae_fit.domain.post.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yung.dongnae_fit.domain.post.entity.Post;
import yung.dongnae_fit.domain.postComment.dto.CommentDataDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class PostContentResponseDTO {

    private Long postId;
    private String memberName;
    private String memberProfile;
    private LocalDateTime postDate;
    private String postTitle;
    private String postDetail;
    private String postImage;
    private boolean postLikeStatus;
    private boolean postSaveStatus;
    private Long postLikeCount;
    private Long postSaveCount;
    private List<CommentDataDTO> comments;

    @Builder
    public PostContentResponseDTO(Post post, boolean postLikeStatus, boolean postSaveStatus, List<CommentDataDTO> comments) {

        this.postId = post.getId();
        this.memberName = post.getMember().getName();
        this.memberProfile = post.getMember().getProfile();
        this.postDate = post.getDate();
        this.postTitle = post.getTitle();
        this.postDetail = post.getDetail();
        this.postImage = post.getImage();
        this.postLikeCount = (long) post.getPostLikes().size();
        this.postSaveCount = (long) post.getPostSaves().size();
        this.postLikeStatus = postLikeStatus;
        this.postSaveStatus = postSaveStatus;
        this.comments = comments;
    }
}
