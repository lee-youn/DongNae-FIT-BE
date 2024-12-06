package yung.dongnae_fit.domain.programSave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.program.entity.Program;
import yung.dongnae_fit.domain.programSave.entity.ProgramSave;

import java.util.Optional;

public interface ProgramSaveRepository extends JpaRepository<ProgramSave, Long> {
    Optional<Object> findByProgramAndMember(Program program, Member member);

    void deleteAllByProgramAndMember(Program program, Member member);
}
