package yung.dongnae_fit.domain.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FacilitiesResponseDTO {
    private Long facilityId;
    private String facilityName;
    private String facilityType;
    private String facilityAddr;
    private Double facilityLatitude;
    private Double facilityLongitude;
    private Double km;

    @Builder
    public void FacilityDTO(Long facilityId, String facilityName, String facilityType,
                            String facilityAddr, Double facilityLatitude,
                            Double facilityLongitude, Double km) {
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.facilityType = facilityType;
        this.facilityAddr = facilityAddr;
        this.facilityLatitude = facilityLatitude;
        this.facilityLongitude = facilityLongitude;
        this.km = km;
    }

}
