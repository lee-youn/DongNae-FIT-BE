package yung.dongnae_fit.domain.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Data
public class PostImageRequestDTO {
    private MultipartFile postImage;
}
