package yung.dongnae_fit.domain.program.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yung.dongnae_fit.domain.programFacility.entity.ProgramFacility;

import java.util.List;

@Data
@NoArgsConstructor
public class ProgramDetailResponseDTO {
    private Long facilityId;
    private String facilityName;
    private String facilityAddr;
    private String facilityImage;
    private Double facilityLatitude;
    private Double facilityLongitude;
    private ProgramDetailDTO programData;
    private List<ReviewDataDTO> reviewData;

    @Builder
    public ProgramDetailResponseDTO(ProgramFacility programFacility, ProgramDetailDTO programData, List<ReviewDataDTO> reviewData) {
        this.facilityId = programFacility.getId();
        this.facilityName = programFacility.getName();
        this.facilityAddr = programFacility.getAddr();
        this.facilityImage = programFacility.getImage();
        this.facilityLatitude = programFacility.getLatitude();
        this.facilityLongitude = programFacility.getLongitude();
        this.programData = programData;
        this.reviewData = reviewData;
    }
}
