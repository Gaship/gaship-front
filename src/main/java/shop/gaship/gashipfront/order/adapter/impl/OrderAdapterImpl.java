package shop.gaship.gashipfront.order.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.cart.exception.ProductStockIsZeroException;
import shop.gaship.gashipfront.order.adapter.OrderAdapter;
import shop.gaship.gashipfront.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipfront.order.dto.request.SuccessOrderRequestDto;
import shop.gaship.gashipfront.order.dto.response.OrderResponseDto;
import shop.gaship.gashipfront.order.exception.CouponProcessException;
import shop.gaship.gashipfront.order.exception.OrderProcessException;
import shop.gaship.gashipfront.util.ExceptionUtil;
import shop.gaship.gashipfront.util.dto.ErrorResponse;

/**
 * 주문 관련 요청을 위한 adapter 구현체입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class OrderAdapterImpl implements OrderAdapter {
    private static final String ORDER_PATH = "/api/orders";
    private final WebClient webClient;


    /**
     * {@inheritDoc}
     */
    @Override
    public OrderResponseDto doOrder(OrderRegisterRequestDto requestDto) {
        return webClient.post()
                .uri(ORDER_PATH)
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    String errorMessage = clientResponse.bodyToMono(ErrorResponse.class).block().getMessage();
                    if(errorMessage.contains("재고")) {
                        throw new ProductStockIsZeroException();
                    } else if(errorMessage.contains("쿠폰")){
                        throw new CouponProcessException();
                    } else {
                        throw new OrderProcessException();
                    }
                })
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(OrderResponseDto.class)
                .blockOptional()
                .orElseThrow(OrderProcessException::new);
    }

    @Override
    public void orderSuccess(SuccessOrderRequestDto requestDto) {
        webClient.put()
                .uri(ORDER_PATH + "/success")
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }
}
