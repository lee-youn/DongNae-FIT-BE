package yung.dongnae_fit.domain.post.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostRequestDTO {
    private String postTitle;
    private String postDetail;
}
