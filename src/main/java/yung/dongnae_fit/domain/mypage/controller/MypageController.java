package yung.dongnae_fit.domain.mypage.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.mypage.dto.MypageGetResponseDTO;
import yung.dongnae_fit.domain.mypage.dto.MypageNameRequestDTO;
import yung.dongnae_fit.domain.mypage.dto.MypageProfileReuqestDTO;
import yung.dongnae_fit.domain.mypage.dto.MypageRegionDTO;
import yung.dongnae_fit.domain.mypage.service.MypageService;
import yung.dongnae_fit.domain.post.dto.PostListResponseDTO;
import yung.dongnae_fit.domain.program.dto.ProgramDataDTO;
import yung.dongnae_fit.global.dto.ResponseDTO;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/mypage")
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
    public ResponseEntity<?> updateName(@RequestBody MypageNameRequestDTO mypageNameRequestDTO) {
        mypageService.updateName(mypageNameRequestDTO.getName());
        MypageGetResponseDTO mypageGetResponseDTO = mypageService.getMember();
        ResponseDTO<?> responseDTO = ResponseDTO.ok("회원정보가 수정되었습니다.", mypageGetResponseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody MypageProfileReuqestDTO mypageProfileReuqestDTO) throws IOException {
        mypageService.uploadProfile(mypageProfileReuqestDTO.getProfile());
        MypageGetResponseDTO mypageGetResponseDTO = mypageService.getMember();
        ResponseDTO<?> responseDTO = ResponseDTO.ok("회원정보가 수정되었습니다.", mypageGetResponseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/programs/save")
    public ResponseEntity<?> getMypageSavedProgram(){
        List<ProgramDataDTO> programDataDTOList = mypageService.getSavedPrograms();
        ResponseDTO<?> responseDTO = ResponseDTO.ok("스포츠강좌목록이 조회되었습니다.", programDataDTOList);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/programs/review")
    public ResponseEntity<?> getMypageReviewedProgram(){
        List<ProgramDataDTO> programDataDTOList = mypageService.getReviewedPrograms();
        ResponseDTO<?> responseDTO = ResponseDTO.ok("스포츠강좌목록이 조회되었습니다.", programDataDTOList);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/posts/post")
    public ResponseEntity<?> getMypagePostedPost(){
        List<PostListResponseDTO> postListResponseDTOList = mypageService.getPostedPosts();
        ResponseDTO<?> responseDTO = ResponseDTO.ok("커뮤니티 목록이 조회되었습니다.", postListResponseDTOList);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/posts/comment")
    public ResponseEntity<?> getMypageCommentedPost(){
        List<PostListResponseDTO> postListResponseDTOList = mypageService.getCommentedPosts();
        ResponseDTO<?> responseDTO = ResponseDTO.ok("커뮤니티 목록이 조회되었습니다.", postListResponseDTOList);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/auth/mypage/posts/save")
    public ResponseEntity<?> getMypageSavedPost(){
        List<PostListResponseDTO> postListResponseDTOList = mypageService.getSavedPosts();
        ResponseDTO<?> responseDTO = ResponseDTO.ok("커뮤니티 목록이 조회되었습니다.", postListResponseDTOList);
        return ResponseEntity.ok(responseDTO);
    }
}
