package yung.dongnae_fit.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MemberCreateDTO {

    @NotNull(message = "name은 반드시 필요합니다.")
    private String name;

    @NotNull(message = "region은 반드시 필요합니다.")
    private String region;

    private String province;
    private String district;
    private Double latitude;
    private Double longitude;
}
