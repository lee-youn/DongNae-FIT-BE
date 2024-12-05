package yung.dongnae_fit.domain.postSave.entity;

import jakarta.persistence.*;
import lombok.*;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.post.entity.Post;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostSave(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

}
