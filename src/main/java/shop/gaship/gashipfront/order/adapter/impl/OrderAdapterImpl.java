package shop.gaship.gashipfront.order.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.order.adapter.OrderAdapter;
import shop.gaship.gashipfront.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipfront.order.dto.response.OrderResponseDto;
import shop.gaship.gashipfront.order.exception.OrderProcessException;
import shop.gaship.gashipfront.util.ExceptionUtil;

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


    @Override
    public OrderResponseDto doOrder(OrderRegisterRequestDto requestDto) {
        return webClient.post()
                .uri(ORDER_PATH)
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(OrderResponseDto.class)
                .blockOptional()
                .orElseThrow(OrderProcessException::new);
    }
}
