package yung.dongnae_fit.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.post.entity.Post;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndMember(Long postId, Member member);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:search% OR p.detail LIKE %:search% ORDER BY p.date DESC")
    List<Post> findByTitleOrDetailLike(String search);

    void deleteByIdAndMember(Long postId, Member member);

    List<Post> findByMember(Member member);

    @Query("SELECT p FROM Post p ORDER BY SIZE(p.postLikes) + SIZE(p.postSaves), p.date DESC")
    List<Post> findAllByOrderByLikesCountDesc();
}
