package yung.dongnae_fit.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.member.repository.MemberRepository;
import yung.dongnae_fit.domain.program.entity.Program;
import yung.dongnae_fit.domain.program.repository.ProgramRepository;
import yung.dongnae_fit.domain.review.dto.ReviewRequestDTO;
import yung.dongnae_fit.domain.review.entity.Review;
import yung.dongnae_fit.domain.review.repository.ReviewRepository;
import yung.dongnae_fit.global.RequestScopedStorage;

@RequiredArgsConstructor
@Log4j2
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final RequestScopedStorage requestScopedStorage;
    private final ProgramRepository programRepository;

    @Transactional
    public void createReview(ReviewRequestDTO reviewRequestDTO, Long programId) {
        String kakaoId = requestScopedStorage.getKakaoId();

        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId));

        Program program = programRepository.findById(programId)
                .orElseThrow(() ->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "프로그램을 찾을 수 없습니다."));

        Review review = Review.builder()
                .reviewPost(reviewRequestDTO.getReviewPost())
                .member(member)
                .program(program)
                .duration(reviewRequestDTO.getReviewDuration())
                .instructor(reviewRequestDTO.getReviewInstructor())
                .build();

        reviewRepository.save(review);
    }
}
