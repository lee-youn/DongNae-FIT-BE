package yung.dongnae_fit.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.member.dto.AuthTokens;
import yung.dongnae_fit.domain.member.dto.LoginResponse;
import yung.dongnae_fit.domain.member.service.KakaoService;
import yung.dongnae_fit.domain.member.service.ReissueService;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final KakaoService kakaoService;
    private final ReissueService reissueService;

    @GetMapping("/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestParam String code){
        try{
            return ResponseEntity.ok(kakaoService.KakaoLogin(code));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Item Not Found");
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthTokens> reissueAccessToken(@RequestParam String refreshToken){
        try{
            return ResponseEntity.ok(reissueService.ReissueToken(refreshToken));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Item Not Found");
        }
    }
}
