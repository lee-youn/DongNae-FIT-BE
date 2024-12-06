package yung.dongnae_fit.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private String id;
    private AuthTokens token;
    private boolean onboard;

    public LoginResponse(String id, AuthTokens token, boolean onboard) {
        this.id = id;
        this.token = token;
        this.onboard = onboard;
    }
}