package yung.dongnae_fit.domain.memberComment.entity;

import jakarta.persistence.*;
import lombok.*;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.postComment.entity.PostComment;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_comment_id")
    private PostComment postComment;

    @Builder
    public MemberComment(Member member, PostComment postComment) {
        this.member = member;
        this.postComment = postComment;
    }
}
