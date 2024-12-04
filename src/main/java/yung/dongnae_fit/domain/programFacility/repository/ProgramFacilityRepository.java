package yung.dongnae_fit.domain.programFacility.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yung.dongnae_fit.domain.facility.entity.Facility;
import yung.dongnae_fit.domain.programFacility.entity.ProgramFacility;

@Repository
public interface ProgramFacilityRepository extends JpaRepository<ProgramFacility, Long> {
}
