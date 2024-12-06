package yung.dongnae_fit.domain.programSave.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.program.entity.Program;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramSave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @Builder
    public ProgramSave(Member member, Program program) {
        this.member = member;
        this.program = program;
    }
}
