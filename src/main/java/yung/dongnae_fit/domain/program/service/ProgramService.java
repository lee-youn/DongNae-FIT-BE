package yung.dongnae_fit.domain.program.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.member.repository.MemberRepository;
import yung.dongnae_fit.domain.post.entity.Post;
import yung.dongnae_fit.domain.postSave.entity.PostSave;
import yung.dongnae_fit.domain.program.dto.ProgramDataDTO;
import yung.dongnae_fit.domain.program.dto.ProgramDetailDTO;
import yung.dongnae_fit.domain.program.dto.ProgramDetailResponseDTO;
import yung.dongnae_fit.domain.program.dto.ReviewDataDTO;
import yung.dongnae_fit.domain.program.entity.Program;
import yung.dongnae_fit.domain.program.repository.ProgramRepository;
import yung.dongnae_fit.domain.programFacility.entity.ProgramFacility;
import yung.dongnae_fit.domain.programFacility.repository.ProgramFacilityRepository;
import yung.dongnae_fit.domain.programSave.entity.ProgramSave;
import yung.dongnae_fit.domain.programSave.repository.ProgramSaveRepository;
import yung.dongnae_fit.global.RequestScopedStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramFacilityRepository programFacilityRepository;
    private final MemberRepository memberRepository;
    private final RequestScopedStorage requestScopedStorage;
    private final ProgramSaveRepository programSaveRepository;

    @Transactional
    public List<ProgramDataDTO> getPrograms(Long min, Long max, String search) {
        double latitude = 37.5178;
        double longitude = 127.0474;
        double radius = 8;  // 기본 반경 2km
        String province = "서울특별시";
        String district = "강남구";

        if (requestScopedStorage.getKakaoId() != null) {
            String kakaoId = requestScopedStorage.getKakaoId();

            Member member = memberRepository.findByKakaoId(kakaoId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId));

            latitude = member.getLatitude();
            longitude = member.getLongitude();
            province = member.getProvince();
            district = member.getDistrict();
        }

        List<Object[]> rawResult = programFacilityRepository.findFacilitiesWithinRadius(latitude, longitude, radius, province, district);
        List<ProgramDataDTO> programDataList = new ArrayList<>();
        if (!rawResult.isEmpty()) {
            for (Object[] result : rawResult) {
                Long facilityId = ((Number) result[0]).longValue(); // 첫 번째 요소는 facilityId
                Double km = ((Number) result[1]).doubleValue();    // 두 번째 요소는 km (거리)

                // 시설 ID를 기반으로 프로그램 검색
                List<Program> facilityPrograms = search != null
                        ? programRepository.findProgramsBySearchAndFacilityId(search, facilityId)
                        : programRepository.findProgramsByFacilityId(facilityId);

                ProgramFacility programFacility = programFacilityRepository.findById(facilityId).orElseThrow();
                for (Program program : facilityPrograms) {
                    ProgramDataDTO dto = new ProgramDataDTO(program, programFacility, km);
                    programDataList.add(dto);
                }
            }
        }

        // 가격 필터링
        if (min != null && max != null) {
            programDataList = programDataList.stream()
                    .filter(dto -> dto.getProgramPrice() >= min && dto.getProgramPrice() <= max)
                    .collect(Collectors.toList());
        }

        return programDataList;
    }

    @Transactional
    public void toggleSave(Long programId) {
        String kakaoId = requestScopedStorage.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId));
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "프로그램을 찾을 수 없습니다."));

        programSaveRepository.findByProgramAndMember(program, member)
                .ifPresentOrElse(
                        programSave -> programSaveRepository.deleteAllByProgramAndMember(program, member),
                        () -> {
                            ProgramSave newProgramSave = ProgramSave.builder()
                                    .program(program)
                                    .member(member)
                                    .build();
                            programSaveRepository.save(newProgramSave);
                        });

    }

    @Transactional
    public ProgramDetailResponseDTO getProgramDetail(Long programId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "프로그램을 찾을 수 없습니다."));

        boolean programSaveStatus = false;

        if (requestScopedStorage.getKakaoId() != null) {
            String kakaoId = requestScopedStorage.getKakaoId();

            Member member = memberRepository.findByKakaoId(kakaoId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. Kakao ID: " + kakaoId));
            programSaveStatus = programSaveRepository.findByProgramAndMember(program, member)
                    .isPresent();
        }

        List<ReviewDataDTO> reviewDataDTOS = program.getReviews().stream()
                .map(ReviewDataDTO::new)
                .toList();
        ProgramFacility programFacility = programFacilityRepository.findById(program.getFacilityId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "프로그램 시설을 찾을 수 없습니다."));

        return new ProgramDetailResponseDTO(programFacility, new ProgramDetailDTO(program,programSaveStatus),reviewDataDTOS);
    }


}
