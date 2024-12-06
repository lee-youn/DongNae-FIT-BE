package yung.dongnae_fit.domain.postComment.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yung.dongnae_fit.domain.postComment.entity.PostComment;
import yung.dongnae_fit.domain.postSave.entity.PostSave;

@NoArgsConstructor
@Data
public class CommentDataDTO {

    private Long commentId;
    private String memberName;
    private String memberProfile;
    private String commentDetail;

    @Builder
    public CommentDataDTO(PostComment postComment) {
        this.commentId = postComment.getId();
        this.memberName = postComment.getMember().getName();
        this.memberProfile = postComment.getMember().getProfile();
        this.commentDetail = postComment.getDetail();
    }

}
