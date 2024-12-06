package yung.dongnae_fit.domain.postComment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.post.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @CreatedDate
    private LocalDateTime createdAt;

//    @OneToMany(mappedBy = "postComment", cascade = CascadeType.ALL)
//    private List<MemberComment> memberComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public PostComment(Post post, String detail, Member member) {
        this.post = post;
        this.detail = detail;
        this.member = member;
    }


//    @Builder
//    public PostComment(Post post, String detail) {
//        this.post = post;
//        this.detail = detail;
//    }
//
//    public void addMemberComment(MemberComment memberComment) {
//        this.memberComments.add(memberComment);
//        memberComment.setPostComment(this);
//    }
}
