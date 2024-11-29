package yung.dongnae_fit.domain.member.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class JwtTokenProvider {

    private final SecretKey key;

    public JwtTokenProvider() {
        String secretKey = "aasdlaksfjlaksfjklasjfklasjfklasjfkatympasfkajflkasfakslfjaklsfasfa";
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);  // Corrected this line
    }

    // Token 유효성 검사 : 유효기간이 지났는지 확인
    @Transactional
    public boolean validateToken(String token) {
        System.out.printf(token);
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            System.out.printf("여기도 됨?");

            // 만료 시간 확인
            Date expiration = claimsJws.getBody().getExpiration();  // exp 클레임
            Date now = new Date();  // 현재 시간

            // 만약 만료 시간이 현재 시간보다 이전이면 false 반환
            if (expiration.before(now)) {
                return false; // 토큰 만료됨
            }

            return true; // 유효한 토큰
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
            System.err.println("토큰 유효성 검사 오류: " + e.getMessage());
            return false; // 유효하지 않은 토큰
        }
    }

    public String getSubjectFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String accessTokenGenerate(String subject, Date expiredAt) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String refreshTokenGenerate(Date expiredAt) {
        return Jwts.builder()
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}