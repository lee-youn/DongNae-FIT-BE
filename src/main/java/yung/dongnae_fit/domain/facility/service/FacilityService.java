package yung.dongnae_fit.domain.facility.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import yung.dongnae_fit.domain.facility.dto.FacilitiesResponseDTO;
import yung.dongnae_fit.domain.facility.repository.FacilityRepository;
import yung.dongnae_fit.domain.member.entity.Member;
import yung.dongnae_fit.domain.member.repository.MemberRepository;
import yung.dongnae_fit.global.RequestScopedStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
@Service
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final MemberRepository memberRepository;
    private final RequestScopedStorage requestScopedStorage;

    @Transactional
    public List<FacilitiesResponseDTO> findFacilities(String type, String search) {

        double latitude = 37.5178;
        double longitude = 127.0474;
        double radius = 8;  // 기본 반경 2km
        String province = "서울특별시";
        String district = "강남구";

        List<Object[]> rawResult;

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

        if (type != null && search != null) {
            rawResult = facilityRepository.findByFilterAndNameContainingWithinRadius(type, search, latitude, longitude, radius, province, district);
        }
        else if (type != null) {
            rawResult = facilityRepository.findByTypeWithinRadius(type, latitude, longitude, radius, province, district);
        }
        else if (search != null) {
            rawResult = facilityRepository.findBySearchWithinRadius(search, latitude, longitude, radius, province, district);
        }
        else {
            rawResult =facilityRepository.findFacilitiesWithinRadius(latitude, longitude, radius, province, district);
        }

        return convertToFacilityDTOList(rawResult);
    }

    private List<FacilitiesResponseDTO> convertToFacilityDTOList(List<Object[]> rawResult) {
        List<FacilitiesResponseDTO> facilityDTOList = new ArrayList<>();

        for (Object[] row : rawResult) {
            Long facilityId = (Long) row[0]; // facilityId
            String facilityName = (String) row[1]; // facilityName
            String facilityType = (String) row[2]; // facilityType
            String facilityAddr = (String) row[3]; // facilityAddr
            Double facilityLatitude = (Double) row[4]; // facilityLatitude
            Double facilityLongitude = (Double) row[5]; // facilityLongitude
            Double km = (Double) row[6]; // km

            FacilitiesResponseDTO dto = new FacilitiesResponseDTO(facilityId, facilityName, facilityType,
                    facilityAddr, facilityLatitude,
                    facilityLongitude, km);
            facilityDTOList.add(dto);
        }

        return facilityDTOList;
    }
}
