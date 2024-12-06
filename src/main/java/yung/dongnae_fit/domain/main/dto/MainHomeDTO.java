package yung.dongnae_fit.domain.main.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yung.dongnae_fit.domain.post.dto.PostListResponseDTO;
import yung.dongnae_fit.domain.program.dto.ProgramDataDTO;

import java.util.List;

@Data
@NoArgsConstructor
public class MainHomeDTO {
    private List<ProgramDataDTO> programData;
    private List<PostListResponseDTO> postData;

    @Builder
    public MainHomeDTO(List<ProgramDataDTO> programData, List<PostListResponseDTO> postData) {
        this.programData = programData;
        this.postData = postData;
    }
}
