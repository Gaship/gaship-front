package shop.gaship.gashipfront.order.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.order.adapter.PaymentAdapter;
import shop.gaship.gashipfront.order.dto.request.OrderPaymentCancelRequestDto;
import shop.gaship.gashipfront.order.dto.request.PaymentSuccessRequestDto;
import shop.gaship.gashipfront.order.exception.PaymentRequestException;
import shop.gaship.gashipfront.order.exception.PaymentServerException;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 *
 *
 * @author : 김세미
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class PaymentAdapterImpl implements PaymentAdapter {
    private final WebClient webClient;

    @Override
    public void successPayment(PaymentSuccessRequestDto requestDto) {
        webClient.post()
                .uri("/payments/success")
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    throw new PaymentServerException();
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    throw new PaymentRequestException();
                })
                .toEntity(void.class)
                .block();
    }

    @Override
    public void cancelPayment(Integer orderNo,
                              OrderPaymentCancelRequestDto orderPaymentCancelRequestDto) {
        webClient.post()
                .uri("/payments/{orderNo}/cancel", orderNo)
                .bodyValue(orderPaymentCancelRequestDto)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }
}
