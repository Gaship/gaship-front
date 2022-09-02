package shop.gaship.gashipfront.order.controller;

import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.order.dto.request.OrderCancelRequestDto;
import shop.gaship.gashipfront.order.dto.request.PaymentSuccessRequestDto;
import shop.gaship.gashipfront.order.service.OrderService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;

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
    private final CartService cartService;

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
            @RequestParam(name = "provider") String provider,
            @AuthenticationPrincipal UserDetailsDto user
            ) {
        orderService.successPayment(PaymentSuccessRequestDto.builder()
                .paymentKey(paymentKey)
                .orderId(orderId.split("gaship-")[1])
                .amount(amount)
                .provider(provider)
                .build());

        cartService.deleteOrderedProductFromCart(user.getMemberNo().toString());

        return "redirect:/member/order-product";
    }

    @GetMapping("/fail")
    public String paymentFail() {
        return "order/paymentFail";
    }

    @PostMapping("/{orderNo}/cancel")
    public String orderCancel(@PathVariable("orderNo") Integer orderNo,
                              @ModelAttribute OrderCancelRequestDto requestDto,
                              @AuthenticationPrincipal UserDetailsDto user) {
        if(Objects.isNull(user)) {
            return "redirect:/";
        }

        orderService.cancelOrder(orderNo, requestDto, user.getMemberNo());

        return "redirect:/member/order-product/" + orderNo + "/details";
    }
}
