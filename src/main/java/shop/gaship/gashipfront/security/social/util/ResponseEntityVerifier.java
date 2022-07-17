package shop.gaship.gashipfront.security.social.util;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import shop.gaship.gashipfront.security.social.exception.ErrorResponse;
import shop.gaship.gashipfront.security.social.exception.NullResponseBodyException;
import shop.gaship.gashipfront.security.social.exception.RequestFailureException;

/**
 * ResponseEntity의 응답 body가 예외인지 검증하기 위한 클래스로서 팀원들이 편하게 service단에서 검증할수 있도록 유틸화 시켰습니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class ResponseEntityVerifier {
    public static void verify(ResponseEntity<Object> response, HttpStatus statusCode) {
        if (Objects.equals(response.getStatusCode(), statusCode)) return;

        ErrorResponse error = (ErrorResponse) response.getBody();
        if (Objects.isNull(error)) throw new NullResponseBodyException();
        throw new RequestFailureException(error.getMessage(), statusCode);
    }
}
