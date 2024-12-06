package yung.dongnae_fit.domain.program.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.member.repository.MemberRepository;
import yung.dongnae_fit.domain.post.entity.Post;
import yung.dongnae_fit.domain.program.dto.ProgramDataDTO;
import yung.dongnae_fit.domain.program.repository.ProgramRepository;
import yung.dongnae_fit.domain.programFacility.repository.ProgramFacilityRepository;
import yung.dongnae_fit.global.RequestScopedStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramFacilityRepository programFacilityRepository;
    private final MemberRepository memberRepository;
    private final RequestScopedStorage requestScopedStorage;

//    @Transactional
//    public List<ProgramDataDTO> getPrograms(Long min, Long max, String search) {
//        String kakaoId = requestScopedStorage.getKakaoId();
//        Member member = memberRepository.findByKakaoId(kakaoId)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId));
//
//        double latitude = member.getLatitude();
//        double longitude = member.getLongitude();
//        double radius = 2;
//        String province = member.getProvince();
//        String district = member.getDistrict();
//
//        List<Object[]> rawResult;
//        if (min != null && max != null && search != null) {
//
//        }
//        else if (min == null && max == null) {
//
//        }
//        else if (search == null) {
//
//        }
//        else {
//
//        }
//    }


}
