package yung.dongnae_fit.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private String id;
    private AuthTokens token;

    public LoginResponse(String id, AuthTokens token) {
        this.id = id;
        this.token = token;
    }
}