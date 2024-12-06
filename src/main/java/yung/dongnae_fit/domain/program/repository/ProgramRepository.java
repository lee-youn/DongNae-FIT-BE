package yung.dongnae_fit.domain.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yung.dongnae_fit.domain.program.entity.Program;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    @Query("SELECT p FROM Program p " +
            "JOIN p.programFacility pf " +
            "WHERE pf.id = :facilityId " +
            "AND (:search IS NULL OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(pf.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Program> findProgramsBySearchAndFacilityId(String search,
                                                    Long facilityId);

    @Query("SELECT p FROM Program p WHERE p.programFacility.id = :facilityId")
    List<Program> findProgramsByFacilityId(Long facilityId);
}
