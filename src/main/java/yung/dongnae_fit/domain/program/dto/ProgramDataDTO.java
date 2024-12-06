package yung.dongnae_fit.domain.program.dto;

import lombok.*;
import yung.dongnae_fit.domain.program.entity.Program;
import yung.dongnae_fit.domain.programFacility.entity.ProgramFacility;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDataDTO {

    private Long programId;
    private String programName;
    private String facilityName;
    private String facilityImage;
    private Double facilityLatitude;
    private Double facilityLongitude;
    private Long programPrice;
    private Double km;

    @Builder
    public ProgramDataDTO(Program program, ProgramFacility programFacility, Double km) {
        this.programId = program.getId();
        this.programName = program.getName();
        this.facilityName = programFacility.getName();
        this.facilityImage = programFacility.getImage();
        this.facilityLatitude = programFacility.getLatitude();
        this.facilityLongitude = programFacility.getLongitude();
        this.programPrice = program.getPrice();
        this.km = km;
    }

}
