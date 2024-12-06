package yung.dongnae_fit.domain.facility.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.facility.dto.FacilitiesResponseDTO;
import yung.dongnae_fit.domain.facility.entity.Facility;
import yung.dongnae_fit.domain.facility.service.FacilityService;
import yung.dongnae_fit.domain.member.service.auth.JwtTokenProvider;
import yung.dongnae_fit.global.RequestScopedStorage;
import yung.dongnae_fit.global.dto.ResponseDTO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class FacilityController {

    private final FacilityService facilityService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RequestScopedStorage requestScopedStorage;

    @GetMapping("/facilities")
    public ResponseEntity<?> findFacilities(@RequestHeader(value = "Authorization", required = false) String token
                                            ,@RequestParam(required = false) String type
                                            ,@RequestParam(required = false) String search) {

        if (token != null) {
            String accessToken = token.substring(7);
            log.info("Access Token: " + accessToken);
            Map<String, Object> claims = jwtTokenProvider.validateToken(accessToken).getBody();
            log.info("Token validation completed. Claims: " + claims);

            String kakaoId = (String) claims.get("sub");
            requestScopedStorage.setKakaoId(kakaoId);
        }

        List<FacilitiesResponseDTO> facilities = facilityService.findFacilities(type, search);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("주변체육시설 목록이 조회되었습니다.", facilities);
        return ResponseEntity.ok(responseDTO);
    }
}
