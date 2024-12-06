package yung.dongnae_fit.domain.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yung.dongnae_fit.domain.review.dto.ReviewRequestDTO;
import yung.dongnae_fit.domain.review.service.ReviewService;
import yung.dongnae_fit.global.dto.ResponseDTO;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/auth/programs/{programId}/review")
    public ResponseEntity<?> createReview(@PathVariable("programId") Long programId,
                                          @RequestBody ReviewRequestDTO reviewRequestDTO) {
        reviewService.createReview(reviewRequestDTO, programId);
        ResponseDTO<?> responseDTO = ResponseDTO.created("후기가 작성되었습니다.");
        return ResponseEntity.ok(responseDTO);
    }
}
