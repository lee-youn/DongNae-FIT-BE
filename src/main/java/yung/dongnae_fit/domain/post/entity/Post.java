package yung.dongnae_fit.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.postLike.entity.PostLike;
import yung.dongnae_fit.domain.postSave.entity.PostSave;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @CreatedDate
    private LocalDateTime date;

    private String image;
    private String title;
    private String detail;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostSave> postSaves = new ArrayList<>();

    @Builder
    public Post(String image, String title, String detail, Member member) {
        this.image = image;
        this.title = title;
        this.detail = detail;
        this.member = member;
    }

}
