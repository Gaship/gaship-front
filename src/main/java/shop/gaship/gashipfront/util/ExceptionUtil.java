package shop.gaship.gashipfront.util;

import java.util.function.Function;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.exceptions.RequestFailureThrow;
import shop.gaship.gashipfront.util.dto.ErrorResponse;

/**
 * 모든 예외를 RequestFailureException으로 처리하는 클래스입니다.
 *
 * @author 김보민, 최겸준, 김민수
 * @since 1.0
 */
public class ExceptionUtil {

    private ExceptionUtil() {
    }


    /**
     * clientResponse 객체를 이용하여 Flux 객체를 받은뒤 Flux error로 만들어 상위 subscriber에게 알려 cancel 요청에 사용합니다.
     * errorMessage와 statusCode를 받아서 차후 statusCode를 처리할상황에서 함께 사용할수 있습니다.
     *
     * @param clientResponse 넘어온 예외에 대한 정보를 가지고 있는 객체입니다.
     * @return Flux&lt;? extends Throwable&gt; Throwable을 상속하는 객체 모두를 대상으로 Flux error로 만듭니다.
     * @author 김민수
     */
    public static Flux<? extends Throwable> createErrorFlux(ClientResponse clientResponse) {
        return clientResponse.bodyToFlux(ErrorResponse.class).flatMap(
            errorResponse -> Flux.error(
                new RequestFailureThrow(errorResponse.getMessage(), clientResponse.statusCode())));
    }

    /**
     * clientResponse 객체를 이용하여 Mono를 받은뒤 해당 모노의 타입을 Mono error로 만들때 사용합니다.
     * errorMessage와 statusCode를 받아서 차후 statusCode를 처리할상황에서 함께 사용할수 있습니다.
     *
     * @param clientResponse 넘어온 예외에 대한 정보를 가지고 있는 객체입니다.
     * @return Mono로서 바디값으로 Throwable을 확장하고있는 객체들을 가집니다.
     * @author 김보민, 최겸준
     */
    public static Mono<? extends Throwable> createErrorMono(ClientResponse clientResponse) {
        Function<ErrorResponse, Mono<? extends Throwable>> errorResponseMonoFunction =
            errorResponse -> Mono.error(
                new RequestFailureThrow(errorResponse.getMessage(), clientResponse.statusCode()));

        return clientResponse.bodyToMono(ErrorResponse.class)
            .flatMap(errorResponseMonoFunction);
    }
}
