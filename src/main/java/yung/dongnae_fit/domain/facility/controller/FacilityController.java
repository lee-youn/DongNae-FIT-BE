package yung.dongnae_fit.domain.facility.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yung.dongnae_fit.domain.facility.dto.FacilitiesResponseDTO;
import yung.dongnae_fit.domain.facility.entity.Facility;
import yung.dongnae_fit.domain.facility.service.FacilityService;
import yung.dongnae_fit.global.dto.ResponseDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @GetMapping
    public ResponseEntity<?> findFacilities(@RequestParam(required = false) String type
                                            ,@RequestParam(required = false) String search) {

        List<FacilitiesResponseDTO> facilities = facilityService.findFacilities(type, search);
        ResponseDTO<?> responseDTO = ResponseDTO.ok("주변체육시설 목록이 조회되었습니다.", facilities);
        return ResponseEntity.ok(responseDTO);
    }
}
