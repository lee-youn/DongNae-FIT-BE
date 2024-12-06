package yung.dongnae_fit.domain.mypage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Data
public class MypageProfileReuqestDTO {
    private MultipartFile profile;
}
