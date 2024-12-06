package yung.dongnae_fit.domain.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.main.dto.MainHomeDTO;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.member.repository.MemberRepository;
import yung.dongnae_fit.domain.post.dto.PostListResponseDTO;
import yung.dongnae_fit.domain.post.entity.Post;
import yung.dongnae_fit.domain.post.repository.PostRepository;
import yung.dongnae_fit.domain.program.dto.ProgramDataDTO;
import yung.dongnae_fit.domain.program.entity.Program;
import yung.dongnae_fit.domain.program.repository.ProgramRepository;
import yung.dongnae_fit.domain.programFacility.entity.ProgramFacility;
import yung.dongnae_fit.domain.programFacility.repository.ProgramFacilityRepository;
import yung.dongnae_fit.global.RequestScopedStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Log4j2
@Service
public class MainService {
    private final MemberRepository memberRepository;
    private final RequestScopedStorage requestScopedStorage;
    private final ProgramFacilityRepository programFacilityRepository;
    private final ProgramRepository programRepository;
    private final PostRepository postRepository;

    @Transactional
    public MainHomeDTO getMainHome() {

        double latitude = 37.5178;
        double longitude = 127.0474;
        double radius = 2;  // 기본 반경 2km
        String province = "서울특별시";
        String district = "강남구";

        if (requestScopedStorage.getKakaoId() != null) {
            String kakaoId = requestScopedStorage.getKakaoId();
            Member member = memberRepository.findByKakaoId(kakaoId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));

            latitude = member.getLatitude();
            longitude = member.getLongitude();
            province = member.getProvince();
            district = member.getDistrict();
        }

        List<Object[]> rawResult = programFacilityRepository.findFacilities(latitude, longitude, province, district);
        List<ProgramDataDTO> programDataList = new ArrayList<>();
        Set<Long> selectedFacilityIds = new HashSet<>(); // 이미 선택된 시설 ID를 추적
        int programCount = 0;

        for (Object[] result : rawResult) {
            Long facilityId = ((Number) result[0]).longValue();
            Double km = ((Number) result[1]).doubleValue();

            // 이미 해당 시설에서 프로그램을 가져왔으면 넘어감
            if (selectedFacilityIds.contains(facilityId)) {
                continue;
            }

            List<Program> facilityPrograms = programRepository.findProgramsByFacilityId(facilityId);
            ProgramFacility programFacility = programFacilityRepository.findById(facilityId).orElseThrow();

            // 시설에서 프로그램이 있으면 첫 번째 프로그램만 선택
            if (!facilityPrograms.isEmpty()) {
                Program program = facilityPrograms.get(0); // 각 시설에서 첫 번째 프로그램을 선택
                ProgramDataDTO dto = new ProgramDataDTO(program, programFacility, km);
                programDataList.add(dto);
                selectedFacilityIds.add(facilityId); // 선택한 시설 ID를 추가
                programCount++;
            }

            // 최소 3개 프로그램이 채워지면 종료
            if (programCount >= 3) {
                break;
            }
        }

        List<Post> posts = postRepository.findAllByOrderByLikesCountDesc();
        List<Post> firstTwoPosts = posts.size() > 2 ? posts.subList(0, 2) : posts;
        List<PostListResponseDTO> postListResponseDTO = firstTwoPosts.stream()
                .map(PostListResponseDTO::new)
                .toList();

        return new MainHomeDTO(programDataList, postListResponseDTO);
    }
}
