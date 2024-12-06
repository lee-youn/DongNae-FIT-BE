package yung.dongnae_fit.domain.postComment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.member.repository.MemberRepository;
import yung.dongnae_fit.domain.post.entity.Post;
import yung.dongnae_fit.domain.post.repository.PostRepository;
import yung.dongnae_fit.domain.postComment.entity.PostComment;
import yung.dongnae_fit.domain.postComment.repository.PostCommentRepository;
import yung.dongnae_fit.global.RequestScopedStorage;

@RequiredArgsConstructor
@Service
public class PostCommentService {

    private final MemberRepository memberRepository;
    private final RequestScopedStorage requestScopedStorage;
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    @Transactional
    public void createComment(Long postId, String comment) {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

        PostComment postComment = PostComment.builder()
                .post(post)
                .member(member)
                .detail(comment)
                .build();
        postCommentRepository.save(postComment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

        PostComment postComment = postCommentRepository.findByIdAndPostAndMember(commentId, post, member)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        postCommentRepository.delete(postComment);
    }
}
