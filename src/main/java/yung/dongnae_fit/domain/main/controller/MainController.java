package yung.dongnae_fit.domain.main.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yung.dongnae_fit.domain.main.dto.MainHomeDTO;
import yung.dongnae_fit.domain.main.dto.MainSearchDTO;
import yung.dongnae_fit.domain.main.service.MainService;
import yung.dongnae_fit.domain.member.service.auth.JwtTokenProvider;
import yung.dongnae_fit.global.RequestScopedStorage;
import yung.dongnae_fit.global.dto.ResponseDTO;

import java.util.Map;


@Log4j2
@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RequestScopedStorage requestScopedStorage;

    @GetMapping("/main/home")
    public ResponseEntity<?> getHome(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null) {
            String accessToken = token.substring(7);
            log.info("Access Token: " + accessToken);
            Map<String, Object> claims = jwtTokenProvider.validateToken(accessToken).getBody();
            log.info("Token validation completed. Claims: " + claims);

            String kakaoId = (String) claims.get("sub");
            requestScopedStorage.setKakaoId(kakaoId);
        }

        MainHomeDTO mainHomeDTO = mainService.getMainHome();
        ResponseDTO<?> responseEntity = ResponseDTO.ok("조회되었습니다.", mainHomeDTO);
        return ResponseEntity.ok(responseEntity);

    }

    @GetMapping("/main")
    public ResponseEntity<?> getHome(@RequestHeader(value = "Authorization", required = false) String token,
                                     @RequestParam(required = false) String search) {
        if (token != null) {
            String accessToken = token.substring(7);
            log.info("Access Token: " + accessToken);
            Map<String, Object> claims = jwtTokenProvider.validateToken(accessToken).getBody();
            log.info("Token validation completed. Claims: " + claims);

            String kakaoId = (String) claims.get("sub");
            requestScopedStorage.setKakaoId(kakaoId);
        }
        MainSearchDTO mainSearchDTO = mainService.getMainSearch(search);
        ResponseDTO<?> responseEntity = ResponseDTO.ok("조회되었습니다.", mainSearchDTO);
        return ResponseEntity.ok(responseEntity);
    }
}