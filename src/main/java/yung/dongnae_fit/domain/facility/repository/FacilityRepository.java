package yung.dongnae_fit.domain.facility.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yung.dongnae_fit.domain.facility.entity.Facility;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
}