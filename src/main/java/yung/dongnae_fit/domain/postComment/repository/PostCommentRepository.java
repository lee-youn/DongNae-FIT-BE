package yung.dongnae_fit.domain.postComment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.post.entity.Post;
import yung.dongnae_fit.domain.postComment.entity.PostComment;

import java.util.Optional;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    Optional<PostComment> findByIdAndPostAndMember(Long commentId, Post post, Member member);
}
