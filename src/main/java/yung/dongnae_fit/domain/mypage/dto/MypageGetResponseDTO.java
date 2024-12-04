package yung.dongnae_fit.domain.mypage.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MypageGetResponseDTO {
    private String name;
    private String region;
    private String profile;

    @Builder
    public MypageGetResponseDTO (String name,  String region, String profile) {
        this.name = name;
        this.region = region;
        this.profile = profile;
    }
}
