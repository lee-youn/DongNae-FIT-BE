package yung.dongnae_fit.domain.member.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.member.dto.AuthTokens;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.member.repository.MemberRepository;
import yung.dongnae_fit.global.RequestScopedStorage;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class ReissueService {
    private static final String BEARER_TYPE = "Bearer";

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider; // JWT 관련 로직 처리 클래스
    private final RequestScopedStorage requestScopedStorage;

    @Value("${jwt.access.expiration}")
    private long ACCESS_TOKEN_EXPIRE_TIME;	//1시간

    @Value("${jwt.refresh.expiration}")
    private long REFRESH_TOKEN_EXPIRE_TIME;  // 14일

    @Transactional
    public AuthTokens ReissueToken(String token) {
        if (jwtTokenProvider.validateToken(token)==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레쉬 토큰입니다.");
        }
        Member member = memberRepository.findByRefreshToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 회원이 존재하지 않습니다."));

        String kakaoId = member.getKakaoId();

        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        String newAccessToken = jwtTokenProvider.accessTokenGenerate(String.valueOf(kakaoId), accessTokenExpiredAt);

        return AuthTokens.of(newAccessToken, token, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }
}
