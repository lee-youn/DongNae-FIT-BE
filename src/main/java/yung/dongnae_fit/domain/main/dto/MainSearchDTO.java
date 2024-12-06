package yung.dongnae_fit.domain.main.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yung.dongnae_fit.domain.facility.dto.FacilitiesResponseDTO;
import yung.dongnae_fit.domain.post.dto.PostListResponseDTO;
import yung.dongnae_fit.domain.program.dto.ProgramDataDTO;

import java.util.List;

@Data
@NoArgsConstructor
public class MainSearchDTO {
    private List<ProgramDataDTO> programData;
    private List<FacilitiesResponseDTO> facilityData;
    private List<PostListResponseDTO> postData;

    @Builder
    public MainSearchDTO(List<ProgramDataDTO> programData, List<FacilitiesResponseDTO> facilityData, List<PostListResponseDTO> postData) {
        this.programData = programData;
        this.facilityData = facilityData;
        this.postData = postData;
    }
}
