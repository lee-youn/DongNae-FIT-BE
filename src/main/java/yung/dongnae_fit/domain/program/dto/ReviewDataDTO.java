package yung.dongnae_fit.domain.program.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yung.dongnae_fit.domain.review.entity.Review;

@Data
@NoArgsConstructor
public class ReviewDataDTO {
    private Long reviewId;
    private String reviewInstructor;
    private String reviewDuration;
    private String reviewPost;

    @Builder
    public ReviewDataDTO(Review review) {
        this.reviewId = review.getId();
        this.reviewInstructor = review.getInstructor();
        this.reviewDuration = review.getDuration();
        this.reviewPost = review.getReviewPost();
    }
}
