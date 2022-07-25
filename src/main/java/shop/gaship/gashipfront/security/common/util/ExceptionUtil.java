package shop.gaship.gashipfront.security.common.util;

import java.util.function.Function;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.security.common.dto.ErrorResponse;
import shop.gaship.gashipfront.security.common.exception.RequestFailureException;

/**
 * 서버의 에러응답을 핸들링하는 객체입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class ExceptionUtil {
    /**
     * 서버에서 오류를 응답할 때 에러를 핸들링하는 메서드입니다.
     *
     * @param clientResponse 서버의 응답 객체입니다.
     * @return Mono에 에러객체를 담아 반환합니다.
     */
    public static Mono<Throwable> createErrorMono(ClientResponse clientResponse) {
        Function<ErrorResponse, Mono<Throwable>> errorResponseMonoFunction =
            errorResponse -> Mono.error(new RequestFailureException(errorResponse.getMessage(),
                clientResponse.statusCode()));

        return clientResponse.bodyToMono(ErrorResponse.class).flatMap(errorResponseMonoFunction);
    }
}
