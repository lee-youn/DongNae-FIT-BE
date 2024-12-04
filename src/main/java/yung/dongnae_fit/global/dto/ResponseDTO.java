package yung.dongnae_fit.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseDTO<D> {

    private final int status;
    private final String message;
    private final D data;

    // OK (200)
    public static <D> ResponseDTO<D> ok(String message) {
        return new ResponseDTO<>(HttpStatus.OK.value(), message, null);
    }
    public static <D> ResponseDTO<D> ok(String message, D data) {
        return new ResponseDTO<>(HttpStatus.OK.value(), message, data);
    }

    // Created (201)
    public static <D> ResponseDTO<D> created(String message) {
        return new ResponseDTO<>(HttpStatus.CREATED.value(), message, null);
    }

    public static <D> ResponseDTO<D> invalid(String message) {
        return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "잘못된 형식입니다.", null);
    }

    public static <D> ResponseDTO<D> badRequest(String message) {
        return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), message,null);
    }

}
