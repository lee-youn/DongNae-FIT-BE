package yung.dongnae_fit.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.review.entity.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMember(Member member);
}
