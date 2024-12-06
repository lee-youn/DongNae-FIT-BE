package yung.dongnae_fit.domain.programFacility.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yung.dongnae_fit.domain.facility.entity.Facility;
import yung.dongnae_fit.domain.programFacility.entity.ProgramFacility;

import java.util.List;

@Repository
public interface ProgramFacilityRepository extends JpaRepository<ProgramFacility, Long> {

    @Query(value = "SELECT pf.id, " +
            "ROUND((6371 * acos(cos(radians(:latitude)) * cos(radians(pf.latitude)) " +
            "* cos(radians(pf.longitude) - radians(:longitude)) + sin(radians(:latitude)) " +
            "* sin(radians(pf.latitude)))), 2) AS km " +
            "FROM program_facility pf " +
            "WHERE pf.province LIKE CONCAT('%', :province, '%') " +
            "HAVING km <= :radius " +
            "ORDER BY km", nativeQuery = true)
    List<Object[]> findFacilitiesWithinRadius(double latitude, double longitude, double radius, String province, String district);

    @Query(value = "SELECT pf.id, " +
            "ROUND((6371 * acos(cos(radians(:latitude)) * cos(radians(pf.latitude)) " +
            "* cos(radians(pf.longitude) - radians(:longitude)) + sin(radians(:latitude)) " +
            "* sin(radians(pf.latitude)))), 2) AS km " +
            "FROM program_facility pf " +
            "WHERE pf.province LIKE CONCAT('%', :province, '%') " +
            "ORDER BY km", nativeQuery = true)
    List<Object[]> findFacilities(double latitude, double longitude, String province, String district);
}
