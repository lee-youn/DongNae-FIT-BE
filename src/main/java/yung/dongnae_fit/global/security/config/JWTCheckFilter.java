package yung.dongnae_fit.global.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.member.service.auth.JwtTokenProvider;
import yung.dongnae_fit.global.RequestScopedStorage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Component
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RequestScopedStorage requestScopedStorage;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        log.info("-------shouldNotFilter--------");
        String requestURI = request.getRequestURI();
        log.info("requestURI: " + requestURI);

        String[] excludePath = {"/member/kakao","/member/refresh", "/swagger-ui", "/v3/api-docs", "/posts", "/facilities", "/programs", "/main",
                "/api/member/kakao","/api/member/refresh", "/api/posts", "/api/facilities", "/api/programs", "/api/main"};

        return Arrays.stream(excludePath).anyMatch(requestURI::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("----------------------JWT Filtering-------------------");

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JWT_TOKEN_REQUIRED");
        }


        try {
            String accessToken = authorization.substring(7);

            // 토큰 검증 단계에서 발생할 수 있는 예외를 디버깅하기 위해 로그 추가
            log.info("Access Token: " + accessToken);

            Map<String, Object> claims = jwtTokenProvider.validateToken(accessToken).getBody();
            log.info("Token validation completed. Claims: " + claims);

            String kakaoId = (String) claims.get("sub");
            requestScopedStorage.setKakaoId(kakaoId);
            System.out.println(requestScopedStorage.getKakaoId());

            log.info("Parsed Claims: kakaoId=" + kakaoId);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT validation failed: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "INVALID_JWT_TOKEN", e);
        }
    }
}
