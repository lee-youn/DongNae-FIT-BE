package yung.dongnae_fit.domain.facility.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yung.dongnae_fit.domain.facility.dto.FacilitiesResponseDTO;
import yung.dongnae_fit.domain.facility.entity.Facility;

import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    @Query(value = "SELECT f.id AS facilityId, f.name AS facilityName, f.type AS facilityType, " +
            "f.addr AS facilityAddr, f.latitude AS facilityLatitude, f.longitude AS facilityLongitude, " +
            "ROUND((6371 * acos(cos(radians(:latitude)) * cos(radians(f.latitude)) " +
            "* cos(radians(f.longitude) - radians(:longitude)) + sin(radians(:latitude)) " +
            "* sin(radians(f.latitude)))), 2) AS km " +
            "FROM facility f " +
            "WHERE f.province LIKE CONCAT('%', :province, '%') " +
            "HAVING km <= :radius " +
            "ORDER BY km", nativeQuery = true)
    List<Object[]> findFacilitiesWithinRadius(double latitude,
                                              double longitude,
                                              double radius,
                                              String province,
                                              String district);



    @Query(value = "SELECT f.id AS facilityId, f.name AS facilityName, f.type AS facilityType, " +
            "f.addr AS facilityAddr, f.latitude AS facilityLatitude, f.longitude AS facilityLongitude, " +
            "ROUND((6371 * acos(cos(radians(:latitude)) * cos(radians(f.latitude)) " +
            "* cos(radians(f.longitude) - radians(:longitude)) + sin(radians(:latitude)) " +
            "* sin(radians(f.latitude)))), 2) AS km " +
            "FROM facility f " +
            "WHERE f.province LIKE CONCAT('%', :province, '%') " +
            "AND f.filter LIKE CONCAT('%', :type, '%') " +
            "AND (LOWER(f.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(f.type) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(f.addr) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "HAVING km <= :radius " +
            "ORDER BY km", nativeQuery = true)
    List<Object[]> findByFilterAndNameContainingWithinRadius(String type,
                                                                          String search,
                                                                          double latitude,
                                                                          double longitude,
                                                                          double radius,
                                                                          String province,
                                                                          String district);

    @Query(value = "SELECT f.id AS facilityId, f.name AS facilityName, f.type AS facilityType, " +
            "f.addr AS facilityAddr, f.latitude AS facilityLatitude, f.longitude AS facilityLongitude, " +
            "ROUND((6371 * acos(cos(radians(:latitude)) * cos(radians(f.latitude)) " +
            "* cos(radians(f.longitude) - radians(:longitude)) + sin(radians(:latitude)) " +
            "* sin(radians(f.latitude)))), 2) AS km " +
            "FROM facility f " +
            "WHERE f.province LIKE CONCAT('%', :province, '%') " +
            "AND f.filter LIKE CONCAT('%', :type, '%') " +
            "HAVING km <= :radius " +
            "ORDER BY km", nativeQuery = true)
    List<Object[]> findByTypeWithinRadius(String type,
                                                       double latitude,
                                                       double longitude,
                                                       double radius,
                                                       String province,
                                                       String district);

    @Query(value = "SELECT f.id AS facilityId, f.name AS facilityName, f.type AS facilityType, " +
            "f.addr AS facilityAddr, f.latitude AS facilityLatitude, f.longitude AS facilityLongitude, " +
            "ROUND((6371 * acos(cos(radians(:latitude)) * cos(radians(f.latitude)) " +
            "* cos(radians(f.longitude) - radians(:longitude)) + sin(radians(:latitude)) " +
            "* sin(radians(f.latitude)))), 2) AS km " +
            "FROM facility f " +
            "WHERE f.province LIKE CONCAT('%', :province, '%') " +
            "AND (LOWER(f.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(f.type) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(f.addr) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "HAVING km <= :radius " +
            "ORDER BY km", nativeQuery = true)
    List<Object[]> findBySearchWithinRadius(String search,
                                                         double latitude,
                                                         double longitude,
                                                         double radius,
                                                         String province,
                                                         String district);
}