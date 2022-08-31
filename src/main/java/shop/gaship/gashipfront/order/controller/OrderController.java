package shop.gaship.gashipfront.order.controller;

import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.gaship.gashipfront.order.dto.request.PaymentSuccessRequestDto;
import shop.gaship.gashipfront.order.service.OrderService;

import java.security.Principal;
import java.util.Objects;


/**
 * 주문 controller 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public String orderForm(Principal principal) {
        if(Objects.isNull(principal)) {
            return "redirect:/";
        }
        return "order/orderForm";
    }

    @GetMapping("/success")
    public String paymentSuccess(
            @ApiParam(required = true) @RequestParam String paymentKey,
            @ApiParam(required = true) @RequestParam String orderId,
            @ApiParam(required = true) @RequestParam Long amount,
            @RequestParam(name = "provider") String provider
    ) {
        orderService.successPayment(PaymentSuccessRequestDto.builder()
                .paymentKey(paymentKey)
                .orderId(orderId.split("gaship-")[1])
                .amount(amount)
                .provider(provider)
                .build());

        return "redirect:/member/order-product";
    }
}
