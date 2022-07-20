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
 * @author : 조재철
 * @since 1.0
 */
@Slf4j
public class WebClientUtil<T> {
    private static final String ERROR_MESSAGE = "응답결과가 존재하지 않습니다.";
    private static final Duration timeOut = Duration.of(3, ChronoUnit.SECONDS);

    /**
     * @param base     url
     * @param urn
     * @param query    params
     * @param headers
     * @param response entity
     * @return response entity
     * @author 조재철
     */
    public ResponseEntity<T> get(String baseUrl, String urn,
                 @Nullable List<QueryParam> queryParams,
                 @Nullable Map<String, List<String>> headers,
                 Class<T> responseEntity) {
        return createMethod(() -> WebClient.create(baseUrl).get()
            , urn, queryParams, headers, responseEntity);
    }

    /**
     * @param <U>      the type parameter
     * @param base     url
     * @param urn
     * @param query    params
     * @param headers
     * @param body     value
     * @param response entity
     * @return response entity
     * @author 조재철
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
     * @param <U>      the type parameter
     * @param base     url
     * @param urn
     * @param query    params
     * @param headers
     * @param body     value
     * @param response entity
     * @return response entity
     * @author 조재철
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
     * @param <U>      the type parameter
     * @param base     url
     * @param urn
     * @param query    params
     * @param headers
     * @param body     value
     * @param response entity
     * @return response entity
     * @author 조재철
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
     * @param base     url
     * @param urn
     * @param query    params
     * @param headers
     * @param response entity
     * @return response entity
     * @author 조재철
     */
    public ResponseEntity<T> delete(String baseUrl, String urn,
                    @Nullable List<QueryParam> queryParams,
                    @Nullable Map<String, List<String>> headers,
                    Class<T> responseEntity) {
        return createMethod(() -> WebClient.create(baseUrl).delete()
            , urn, queryParams, headers, responseEntity);
    }

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

    private UriBuilder setQueryParams(UriBuilder builder, List<QueryParam> queryParams) {
        if (Objects.nonNull(queryParams)) {
            log.debug("queryParams len : {}", queryParams.size());
            queryParams.forEach(queryParam ->
                builder.queryParam(queryParam.getName(), queryParam.getValues()));
        }

        return builder;
    }

    private void setHeaders(HttpHeaders httpHeaders, Map<String, List<String>> headers) {
        if (Objects.nonNull(headers)) {
            httpHeaders.putAll(headers);
        }
    }
}
