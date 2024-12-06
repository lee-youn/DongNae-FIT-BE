package yung.dongnae_fit.domain.program.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yung.dongnae_fit.domain.member.service.auth.JwtTokenProvider;
import yung.dongnae_fit.domain.program.dto.ProgramDataDTO;
import yung.dongnae_fit.domain.program.service.ProgramService;
import yung.dongnae_fit.global.RequestScopedStorage;
import yung.dongnae_fit.global.dto.ResponseDTO;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;
    private final RequestScopedStorage requestScopedStorage;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/programs")
    public ResponseEntity<?> getPrograms(@RequestParam(required = false) Long min,
                                         @RequestParam(required = false) Long max,
                                         @RequestParam(required = false) String search,
                                         @RequestHeader(value = "Authorization", required = false) String token) {

        if (token != null) {
            String accessToken = token.substring(7);
            log.info("Access Token: " + accessToken);
            Map<String, Object> claims = jwtTokenProvider.validateToken(accessToken).getBody();
            log.info("Token validation completed. Claims: " + claims);

            String kakaoId = (String) claims.get("sub");
            requestScopedStorage.setKakaoId(kakaoId);
        }

        List<ProgramDataDTO> programDataDTOList = programService.getPrograms(min, max, search);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("스포츠강좌목록이 조회되었습니다.", programDataDTOList);
        return ResponseEntity.ok(responseDTO);
    }

}
