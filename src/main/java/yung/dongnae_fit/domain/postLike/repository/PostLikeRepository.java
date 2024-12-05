package yung.dongnae_fit.domain.postLike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.post.entity.Post;
import yung.dongnae_fit.domain.postLike.entity.PostLike;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostAndMember(Post post, Member member);

    void deleteAllByPostAndMember(Post post, Member member);
}
