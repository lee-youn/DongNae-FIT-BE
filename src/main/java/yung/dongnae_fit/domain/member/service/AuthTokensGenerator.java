package yung.dongnae_fit.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import yung.dongnae_fit.domain.member.dto.AuthTokens;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthTokensGenerator {
    private static final String BEARER_TYPE = "Bearer";

    @Value("${jwt.access.expiration}")
    private long ACCESS_TOKEN_EXPIRE_TIME;	//1시간

    @Value("${jwt.refresh.expiration}")
    private long REFRESH_TOKEN_EXPIRE_TIME;  // 14일

    private final JwtTokenProvider jwtTokenProvider;

    //id 받아 Access Token 생성
    public AuthTokens generate(String uid) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        //String subject = email.toString();
        String accessToken = jwtTokenProvider.accessTokenGenerate(uid, accessTokenExpiredAt);
        String refreshToken = jwtTokenProvider.refreshTokenGenerate(refreshTokenExpiredAt);

        return AuthTokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }
}