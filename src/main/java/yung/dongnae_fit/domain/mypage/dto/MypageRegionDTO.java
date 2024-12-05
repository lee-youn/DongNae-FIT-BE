package yung.dongnae_fit.domain.mypage.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MypageRegionDTO {

    private String region;
    private String province;
    private String district;
    private Double latitude;
    private Double longitude;

    @Builder
    public MypageRegionDTO(String region, String province, String district, Double latitude, Double longitude) {
        this.region = region;
        this.province = province;
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
