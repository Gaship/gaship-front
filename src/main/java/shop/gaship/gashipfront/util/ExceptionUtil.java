package shop.gaship.gashipfront.util;

import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.exceptions.RequestFailureException;
import shop.gaship.gashipfront.message.ErrorResponse;

/**
 * packageName    : shop.gaship.gashipfront.util
 * fileName       : ExceptionUtil
 * author         : 김보민
 * date           : 2022-07-14
 * description    : 웹 클라이언트에 에러가 발생 했을시에 대한 에러 유틸 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-14        김보민       최초 생성
 */
public class ExceptionUtil {

    private ExceptionUtil() {
    }

    public static Mono<? extends Throwable> createErrorMono(ClientResponse response) {
        return response.bodyToMono(ErrorResponse.class).flatMap(
                body -> Mono.error(new RequestFailureException(body)));
    }
}
