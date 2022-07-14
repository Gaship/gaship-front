package shop.gaship.gashipfront.util;

import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.exceptions.RequestFailureException;
import shop.gaship.gashipfront.message.ErrorResponse;

import java.util.Objects;

/**
 * packageName    : shop.gaship.gashipfront.util
 * fileName       : ExceptionUtil
 * author         : 김보민
 * date           : 2022-07-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-14        김보민       최초 생성
 */
public class ExceptionUtil {

    private ExceptionUtil() {
    }

    public static Mono<? extends Throwable> createErrorMono(ClientResponse response) {
        return Mono.create(throwableMonoSink ->
                throwableMonoSink.error(
                        new RequestFailureException(
                                Objects.requireNonNull(
                                        response.toEntity(ErrorResponse.class)
                                                .block()
                                                .getBody())
                        ))
        );
    }
}
