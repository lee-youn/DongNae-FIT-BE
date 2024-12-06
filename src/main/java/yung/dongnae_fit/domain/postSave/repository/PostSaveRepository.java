package yung.dongnae_fit.domain.postSave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.post.entity.Post;
import yung.dongnae_fit.domain.postSave.entity.PostSave;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostSaveRepository extends JpaRepository<PostSave, Long> {
    Optional<PostSave> findByPostAndMember(Post post, Member member);

    void deleteAllByPostAndMember(Post post, Member member);

    List<PostSave> findByMember(Member member);
}
