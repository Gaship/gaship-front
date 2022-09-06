package shop.gaship.gashipfront.order.controller;

import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.order.dto.request.OrderCancelRequestDto;
import shop.gaship.gashipfront.order.dto.request.PaymentSuccessRequestDto;
import shop.gaship.gashipfront.order.dto.request.SuccessOrderRequestDto;
import shop.gaship.gashipfront.order.exception.PaymentRequestException;
import shop.gaship.gashipfront.order.exception.PaymentServerException;
import shop.gaship.gashipfront.order.service.OrderService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import java.security.Principal;
import java.text.NumberFormat;
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
@Slf4j
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
            @AuthenticationPrincipal UserDetailsDto user,
            RedirectAttributes redirectAttributes
            ) {
        Integer orderNo = Integer.parseInt(orderId.split("gaship-")[1]);

        try {
            orderService.successPayment(PaymentSuccessRequestDto.builder()
                    .paymentKey(paymentKey)
                    .orderId(orderNo.toString())
                    .amount(amount)
                    .provider(provider)
                    .build());

        } catch (PaymentRequestException e) {
            log.error("message : {} , error : {}", e.getMessage(), e.getCause());

            return "redirect:/order/fail";

        } catch (PaymentServerException e) {
            orderService.successOrder(SuccessOrderRequestDto
                    .builder()
                            .orderNo(orderNo)
                            .paymentKey(paymentKey)
                    .build());
            log.error("message : {} , error : {}", e.getMessage(), e.getCause());

            cartService.deleteOrderedProductFromCart(user.getMemberNo().toString());
            redirectAttributes.addFlashAttribute("orderSuccessMsg",
                    "주문이 완료되었습니다.\n주문번호 : " + orderNo
                            + "\n결제 완료 금액 : " + NumberFormat.getInstance().format(amount) + "원");

            return "redirect:/member/order-product/" + orderNo + "/details";
        }

        cartService.deleteOrderedProductFromCart(user.getMemberNo().toString());
        redirectAttributes.addFlashAttribute("orderSuccessMsg",
                "주문이 완료되었습니다.\n주문번호 : " + orderNo
                        + "\n결제 완료 금액 : " + NumberFormat.getInstance().format(amount) + "원");

        return "redirect:/member/order-product/" + orderNo + "/details";
    }

    @GetMapping("/fail")
    public String paymentFail() {
        return "order/paymentFail";
    }

    @PostMapping("/{orderNo}/cancel")
    public String orderCancel(@PathVariable("orderNo") Integer orderNo,
                              @ModelAttribute OrderCancelRequestDto requestDto,
                              @AuthenticationPrincipal UserDetailsDto user,
                              RedirectAttributes redirectAttributes) {

        if(Objects.isNull(user)) {
            return "redirect:/";
        }

        orderService.cancelOrder(orderNo, requestDto, user.getMemberNo());

        String cancelReason = requestDto.getCancelReason();

        redirectAttributes.addFlashAttribute("cancelSuccessMsg",
                "상품이 정상적으로 " + cancelReason + "(가)이 완료되었습니다. \n"
                        + cancelReason + "된 주문상품번호 : " + requestDto.getCancelOrderProductNo()
                        + "\n환불 금액 : " + NumberFormat.getInstance().format(requestDto.getCancelAmount()) + "원");

        return "redirect:/member/order-product/" + orderNo + "/details";
    }
}
