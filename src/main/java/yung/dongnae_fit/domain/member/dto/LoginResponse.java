package yung.dongnae_fit.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private Long id;
    private AuthTokens token;

    public LoginResponse(Long id, AuthTokens token) {
        this.id = id;
        this.token = token;
    }
}