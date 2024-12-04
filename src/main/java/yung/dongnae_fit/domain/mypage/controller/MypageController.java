package yung.dongnae_fit.domain.mypage.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.mypage.dto.MypageGetResponseDTO;
import yung.dongnae_fit.domain.mypage.dto.MypageRegionDTO;
import yung.dongnae_fit.domain.mypage.service.MypageService;
import yung.dongnae_fit.global.dto.ResponseDTO;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping
    public ResponseEntity<?> getMypage() {
        MypageGetResponseDTO mypageGetResponseDTO = mypageService.getMember();
        ResponseDTO<?> responseDTO = ResponseDTO.ok("회원정보가 조회되었습니다.", mypageGetResponseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/region")
    public ResponseEntity<?> updateRegion(@RequestBody MypageRegionDTO mypageRegionDTO) {
        mypageService.updateRegion(mypageRegionDTO);
        MypageGetResponseDTO mypageGetResponseDTO = mypageService.getMember();
        ResponseDTO<?> responseDTO = ResponseDTO.ok("회원정보가 수정되었습니다.", mypageGetResponseDTO);
        return ResponseEntity.ok(responseDTO);
    }


    @PutMapping("/name")
    public ResponseEntity<?> updateName(@RequestBody String name) {
        mypageService.updateName(name);
        MypageGetResponseDTO mypageGetResponseDTO = mypageService.getMember();
        ResponseDTO<?> responseDTO = ResponseDTO.ok("회원정보가 수정되었습니다.", mypageGetResponseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody MultipartFile profile) throws IOException {
        mypageService.uploadProfile(profile);
        MypageGetResponseDTO mypageGetResponseDTO = mypageService.getMember();
        ResponseDTO<?> responseDTO = ResponseDTO.ok("회원정보가 수정되었습니다.", mypageGetResponseDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
