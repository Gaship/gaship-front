package shop.gaship.gashipfront.security.social.common.util;

import java.util.function.Function;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.security.social.common.dto.ErrorResponse;
import shop.gaship.gashipfront.security.social.common.exception.RequestFailureException;

/**
 * @author : 최겸준
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
