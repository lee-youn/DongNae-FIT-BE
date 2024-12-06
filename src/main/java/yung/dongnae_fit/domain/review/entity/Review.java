package yung.dongnae_fit.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.program.entity.Program;

import java.time.LocalDateTime;

@Entity
@Log4j2
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @CreatedDate
    private LocalDateTime date;

    private String instructor;
    private String duration;
    private String reviewPost;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @Builder
    public Review(Member member, Program program, String instructor, String duration, String reviewPost) {
        this.member = member;
        this.program = program;
        this.instructor = instructor;
        this.duration = duration;
        this.reviewPost = reviewPost;
    }
}
