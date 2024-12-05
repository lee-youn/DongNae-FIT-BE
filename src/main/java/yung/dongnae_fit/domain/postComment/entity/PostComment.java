package yung.dongnae_fit.domain.postComment.entity;

import jakarta.persistence.*;
import lombok.*;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.memberComment.entity.MemberComment;
import yung.dongnae_fit.domain.post.entity.Post;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "postComment", cascade = CascadeType.ALL)
    private List<MemberComment> memberComment = new ArrayList<>();


    @Builder
    public PostComment(Post post, List<MemberComment> memberComment) {
        this.post = post;
        this.memberComment = memberComment;
    }
}
