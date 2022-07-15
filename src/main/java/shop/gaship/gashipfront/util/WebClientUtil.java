package shop.gaship.gashipfront.util;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import shop.gaship.gashipfront.exceptions.NoResponseDataException;

/**
 * WebClient 객체를 조금 더 쉽게 사용할 수 있도록 한 번 감싸 만든 유틸리티 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 * @deprecated 팀원간의 투표를 통해서 사용하지않는 것으로 컨벤션을 정했음
 */
@Slf4j
@Deprecated(forRemoval = true, since = "1.0")
public class WebClientUtil<T> {
    private static final String ERROR_MESSAGE = "응답결과가 존재하지 않습니다.";
    private static final Duration timeOut = Duration.of(3, ChronoUnit.SECONDS);

    /**
     * REST API get 메서드를 요청해주는 유틸성 메서드입니다.
     *
     * @param baseUrl : 기본 url
     * @param urn : 서버에 요청 할 urn입니다.
     * @param queryParams : 쿼리 파라미터들입니다.
     * @param headers : http 헤더들입니다.
     * @param responseEntity : 요청받는 객체 타입입니다.
     * @return ResponseEntity<T> : 서버의 응답 값이 담긴 객체입니다.
     */
    public ResponseEntity<T> get(String baseUrl, String urn,
                 @Nullable List<QueryParam> queryParams,
                 @Nullable Map<String, List<String>> headers,
                 Class<T> responseEntity) {
        return createMethod(() -> WebClient.create(baseUrl).get()
            , urn, queryParams, headers, responseEntity);
    }

    /**
     * REST API post 메서드를 요청해주는 유틸성 메서드입니다.
     *
     * @param baseUrl : 기본 url
     * @param urn : 서버에 요청 할 urn입니다.
     * @param queryParams : 쿼리 파라미터들입니다.
     * @param headers : http 헤더들입니다.
     * @param bodyValue : 서버에게 필요한 body data를 제공하기 위해 사용합니다..
     * @param responseEntity : 요청받는 객체 타입입니다.
     * @return ResponseEntity<T> : 서버의 응답 값이 담긴 객체입니다.
     */
    public <U> ResponseEntity<T> post(String baseUrl, String urn,
                      @Nullable List<QueryParam> queryParams,
                      @Nullable Map<String, List<String>> headers,
                      U bodyValue,
                      Class<T> responseEntity) {
        return createHasBodyMethod(() -> WebClient.create(baseUrl).post()
            , urn, queryParams, headers, bodyValue, responseEntity);
    }

    /**
     * REST API put 메서드를 요청해주는 유틸성 메서드입니다.
     *
     * @param baseUrl : 기본 url
     * @param urn : 서버에 요청 할 urn입니다.
     * @param queryParams : 쿼리 파라미터들입니다.
     * @param headers : http 헤더들입니다.
     * @param bodyValue : 서버에게 필요한 body data를 제공하기 위해 사용합니다..
     * @param responseEntity : 요청받는 객체 타입입니다.
     * @return ResponseEntity<T> : 서버의 응답 값이 담긴 객체입니다.
     */
    public <U> ResponseEntity<T> put(String baseUrl, String urn,
                     @Nullable List<QueryParam> queryParams,
                     @Nullable Map<String, List<String>> headers,
                     U bodyValue,
                     Class<T> responseEntity) {
        return createHasBodyMethod(() -> WebClient.create(baseUrl).put()
            , urn, queryParams, headers, bodyValue, responseEntity);
    }


    /**
     * REST API patch 메서드를 요청해주는 유틸성 메서드입니다.
     *
     * @param baseUrl : 기본 url
     * @param urn : 서버에 요청 할 urn입니다.
     * @param queryParams : 쿼리 파라미터들입니다.
     * @param headers : http 헤더들입니다.
     * @param bodyValue : 서버에게 필요한 body data를 제공하기 위해 사용합니다..
     * @param responseEntity : 요청받는 객체 타입입니다.
     * @return ResponseEntity<T> : 서버의 응답 값이 담긴 객체입니다.
     */
    public <U> ResponseEntity<T> patch(String baseUrl, String urn,
                       @Nullable List<QueryParam> queryParams,
                       @Nullable Map<String, List<String>> headers,
                       U bodyValue,
                       Class<T> responseEntity) {
        return createHasBodyMethod(() -> WebClient.create(baseUrl).patch()
            , urn, queryParams, headers, bodyValue, responseEntity);
    }

    /**
     * REST API delete 메서드를 요청해주는 유틸성 메서드입니다.
     *
     * @param baseUrl : 기본 url
     * @param urn : 서버에 요청 할 urn입니다.
     * @param queryParams : 쿼리 파라미터들입니다.
     * @param headers : http 헤더들입니다.
     * @param responseEntity : 요청받는 객체 타입입니다.
     * @return ResponseEntity<T> : 서버의 응답 값이 담긴 객체입니다.
     */
    public ResponseEntity<T> delete(String baseUrl, String urn,
                    @Nullable List<QueryParam> queryParams,
                    @Nullable Map<String, List<String>> headers,
                    Class<T> responseEntity) {
        return createMethod(() -> WebClient.create(baseUrl).delete()
            , urn, queryParams, headers, responseEntity);
    }

    /**
     * Body data가 없는 Http method를 생성하는 메서드입니다.
     *
     * @param restMethod GET, POST, PUT, DELETE 메서드를을 웹 클라이언트를 통해서 생성하는 람다식을 받습니다.
     * @param urn : 서버에 요청 할 urn입니다.
     * @param queryParams : 쿼리 파라미터들입니다.
     * @param headers : http 헤더들입니다.
     * @param responseEntity : 요청받는 객체 타입입니다.
     * @return ResponseEntity 객체가 리턴됩니다.
     */
    private ResponseEntity<T> createMethod(Supplier<WebClient.RequestHeadersUriSpec<?>> restMethod, String urn,
                               List<QueryParam> queryParams,
                               Map<String, List<String>> headers, Class<T> responseEntity) {
        return restMethod.get()
            .uri(uriBuilder -> setQueryParams(uriBuilder.path(urn), queryParams).build())
            .headers(httpHeaders -> setHeaders(httpHeaders, headers))
            .retrieve()
            .toEntity(responseEntity)
            .timeout(timeOut)
            .blockOptional()
            .orElseThrow(() -> new NoResponseDataException(ERROR_MESSAGE));
    }

    /**
     * Body data가 있는 Http method를 생성하는 메서드입니다.
     *
     * @see WebClient.RequestBodyUriSpec WebClient의 RequestBodyUriSpec 참고
     * @see ResponseEntity ResponseEntity 참고
     * @param restMethod GET, POST, PUT, DELETE 메서드를을 웹 클라이언트를 통해서 생성하는 람다식을 받습니다.
     * @param urn : 서버에 요청 할 urn입니다.
     * @param queryParams : 쿼리 파라미터들입니다.
     * @param headers : http 헤더들입니다.
     * @param responseEntity : 요청받는 객체 타입입니다.
     * @return ResponseEntity 객체가 리턴됩니다.
     */
    private <U> ResponseEntity<T> createHasBodyMethod(Supplier<WebClient.RequestBodyUriSpec> restMethod, String urn,
                                      List<QueryParam> queryParams,
                                      Map<String, List<String>> headers, U bodyValue,
                                      Class<T> responseEntity) {
        return restMethod.get()
            .uri(uriBuilder -> setQueryParams(uriBuilder.path(urn), queryParams).build())
            .headers(httpHeaders -> setHeaders(httpHeaders, headers))
            .bodyValue(bodyValue)
            .retrieve()
            .toEntity(responseEntity)
            .timeout(timeOut)
            .blockOptional()
            .orElseThrow(() -> new NoResponseDataException(ERROR_MESSAGE));
    }


    /**
     *  쿼리 파라미터들을 uri에 추가하는 메서드입니다.
     *
     * @see UriBuilder UriBuilder 참고
     * @param builder UriBuilder 객체를 받습니다.
     * @param queryParams : 쿼리 파라미터들입니다.
     * @return UriBuilder 객체를 리턴합니다.
     */
    private UriBuilder setQueryParams(UriBuilder builder, List<QueryParam> queryParams) {
        if (Objects.nonNull(queryParams)) {
            log.debug("queryParams len : {}", queryParams.size());
            queryParams.forEach(queryParam ->
                builder.queryParam(queryParam.getName(), queryParam.getValues()));
        }

        return builder;
    }

    /**
     * 헤더들을 보낼 요청에 추가하는 메서드입니다.
     *
     * @see UriBuilder UriBuilder 참고
     * @param httpHeaders UriBuilder 객체를 받습니다.
     * @param headers : 쿼리 파라미터들입니다.
     * @return UriBuilder 객체를 리턴합니다.
     */
    private void setHeaders(HttpHeaders httpHeaders, Map<String, List<String>> headers) {
        if (Objects.nonNull(headers)) {
            httpHeaders.putAll(headers);
        }
    }
}
