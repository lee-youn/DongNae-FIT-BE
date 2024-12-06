package yung.dongnae_fit.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.member.dto.AuthTokens;
import yung.dongnae_fit.domain.member.dto.CheckNameDTO;
import yung.dongnae_fit.domain.member.dto.LoginResponse;
import yung.dongnae_fit.domain.member.dto.MemberCreateDTO;
import yung.dongnae_fit.domain.member.service.MemberService;
import yung.dongnae_fit.domain.member.service.auth.KakaoService;
import yung.dongnae_fit.domain.member.service.auth.ReissueService;
import yung.dongnae_fit.global.dto.ResponseDTO;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final KakaoService kakaoService;
    private final ReissueService reissueService;
    private final MemberService memberService;

    @GetMapping("/member/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestParam String code){
        try{
            return ResponseEntity.ok(kakaoService.KakaoLogin(code));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"잘못된 형식입니다.");
        }
    }

    @GetMapping("/member/refresh")
    public ResponseEntity<AuthTokens> reissueAccessToken(@RequestParam String refreshToken){
        try{
            return ResponseEntity.ok(reissueService.ReissueToken(refreshToken));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"잘못된 형식입니다.");
        }
    }

    @PostMapping("/auth/member/onboard")
    public ResponseEntity<?> onboardMember(@Valid @RequestBody MemberCreateDTO memberCreateDTO){
        memberService.makeMember(memberCreateDTO);
        ResponseDTO<?> responseDTO = ResponseDTO.created("등록되었습니다.");
        return ResponseEntity.status(201).body(responseDTO);

    }

    @GetMapping("/auth/member/check")
    public ResponseEntity<?> checkMember(@RequestParam CheckNameDTO checkNameDTO){
        boolean check = memberService.checkName(checkNameDTO.getName());
        if(check){
            ResponseDTO<?> responseDTO = ResponseDTO.ok("중복되지 않습니다.");
            return ResponseEntity.status(200).body(responseDTO);
        } else {
            ResponseDTO<?> responseDTO = ResponseDTO.badRequest("중복됩니다. 다른 닉네임으로 수정해주세요.");
            return ResponseEntity.status(400).body(responseDTO);
        }
    }
}
