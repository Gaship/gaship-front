package shop.gaship.gashipfront.orderproduct.adapter.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.orderproduct.adapter.OrderProductAdapter;
import shop.gaship.gashipfront.orderproduct.dto.response.DeliveryInfoResponseDto;
import shop.gaship.gashipfront.orderproduct.dto.response.OrderProductDetailResponseDto;
import shop.gaship.gashipfront.orderproduct.dto.response.OrderProductResponseDto;
import shop.gaship.gashipfront.security.common.exception.NullResponseBodyException;
import shop.gaship.gashipfront.util.ExceptionUtil;
import shop.gaship.gashipfront.util.TokenInjectUtil;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 조재철
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class OrderProductAdapterImpl implements OrderProductAdapter {

    public static final String ORDER_PRODUCT_PREFIX_URL = "/api/order-products";
    public static final String DELIVERY_HOST = "http://localhost:9090";
    private final WebClient webClient;
    private final TokenInjectUtil tokenInjectUtil;

    @Override
    public PageResponse<OrderProductResponseDto> findOrderProductListByMemberNo(
        Pageable pageable, Integer memberNo) {
        return webClient.get()
            .uri(ORDER_PRODUCT_PREFIX_URL + "/member/" + memberNo + "?page=" + pageable.getPageNumber()
                + "&size="
                + pageable.getPageSize())
            .headers(tokenInjectUtil.headersConsumer())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<OrderProductResponseDto>>() {
            })
            .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public List<DeliveryInfoResponseDto> findDeliveryInfoByTrackingNo(String trackingNo) {
        WebClient webClientDelivery = WebClient.builder()
            .baseUrl(DELIVERY_HOST)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

        return webClientDelivery.get()
            .uri("/tracking-no?trackingNo=" + trackingNo)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<List<DeliveryInfoResponseDto>>() {
            })
            .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public PageResponse<OrderProductDetailResponseDto> findOrderProductDetail(Integer orderNo, Integer memberNo, Pageable pageable) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(ORDER_PRODUCT_PREFIX_URL + "/" + orderNo)
                .queryParam("memberNo", memberNo)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<OrderProductDetailResponseDto>>() {
            })
            .blockOptional().orElseThrow(NullResponseBodyException::new);
    }
}
