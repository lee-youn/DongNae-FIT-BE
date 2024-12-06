package yung.dongnae_fit.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.member.dto.MemberCreateDTO;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.member.repository.MemberRepository;
import yung.dongnae_fit.global.RequestScopedStorage;

import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final RequestScopedStorage requestScopedStorage;

    public void makeMember(MemberCreateDTO memberCreateDTO) {
        String kakaoId = requestScopedStorage.getKakaoId();
        Optional<Member> member = memberRepository.findByKakaoId(kakaoId);

        if (member.isPresent()) {
            Member upsateMember = member.get();

            upsateMember.setName(memberCreateDTO.getName());
            upsateMember.setRegion(memberCreateDTO.getRegion());
            upsateMember.setProvince(memberCreateDTO.getProvince());
            upsateMember.setDistrict(memberCreateDTO.getDistrict());
            upsateMember.setLongitude(memberCreateDTO.getLongitude());
            upsateMember.setLatitude(memberCreateDTO.getLatitude());
            memberRepository.save(upsateMember);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId);
        }
    }

    public boolean checkName(String name) {
        Member member = memberRepository.findByName(name).orElse(null);

        if (member == null) {
            return true;
        }
        return false;
    }
}
