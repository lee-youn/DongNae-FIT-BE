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

    private String nickname;

    private String region;

    private Long kakaoId;

    private String refreshToken;

    @Builder
    public Member(Long kakaoId, String refreshToken) {
        this.kakaoId = kakaoId;
        this.refreshToken = refreshToken;
    }
}
