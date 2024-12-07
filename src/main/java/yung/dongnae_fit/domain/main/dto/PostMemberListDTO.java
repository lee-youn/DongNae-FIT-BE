package yung.dongnae_fit.domain.main.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.post.entity.Post;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostMemberListDTO {
    private Long postId;
    private String postTitle;
    private String  postDetail;
    private LocalDateTime postDate;
    private String memberName;
    private String memberProfile;
    private Long postLikeCount;
    private Long postSaveCount;

    @Builder
    public PostMemberListDTO(Post post, Member member) {
        this.postId = post.getId();
        this.postTitle = post.getTitle();
        this.postDetail = post.getDetail();
        this.postDate = post.getDate();
        this.memberName = member.getName();
        this.memberProfile = member.getProfile();
        this.postLikeCount = (long) post.getPostLikes().size();
        this.postSaveCount = (long) post.getPostSaves().size();
    }
}
