package yung.dongnae_fit.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profile;

    private String name;

    private String region;

    private String province;

    private String district;

    private Double latitude;

    private Double longitude;

    private String kakaoId;

    private String profileImage;

    private String refreshToken;

    @Builder
    public Member(String kakaoId, String refreshToken) {
        this.kakaoId = kakaoId;
        this.refreshToken = refreshToken;
    }
}
