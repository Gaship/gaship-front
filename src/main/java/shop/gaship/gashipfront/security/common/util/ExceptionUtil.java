package shop.gaship.gashipfront.security.common.util;

import java.util.function.Function;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.security.common.dto.ErrorResponse;
import shop.gaship.gashipfront.security.common.exception.RequestFailureException;

/**
 * @author : 김보민
 * @since 1.0
 */
public class ExceptionUtil {
    public static Mono<? extends Throwable> createErrorMono(ClientResponse clientResponse) {
        Function<ErrorResponse, Mono<? extends Throwable>> errorResponseMonoFunction
            = errorResponse
                -> Mono.error(new RequestFailureException(errorResponse.getMessage(), clientResponse.statusCode()));

        return clientResponse.bodyToMono(ErrorResponse.class)
            .flatMap(errorResponseMonoFunction);
    }
}