package yung.dongnae_fit.domain.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yung.dongnae_fit.domain.program.entity.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
}
