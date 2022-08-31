package shop.gaship.gashipfront.order.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipfront.cart.dto.response.ProductResponseDto;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.coupon.member.dto.response.UnusedMemberCouponResponseDto;
import shop.gaship.gashipfront.coupon.member.service.CouponMemberService;
import shop.gaship.gashipfront.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipfront.order.dto.response.OrderResponseDto;
import shop.gaship.gashipfront.order.service.OrderService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;

/**
 * 주문 관련 요청을 처리하기 위한 rest controller 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/order")
public class OrderRestController {
    private final CartService cartService;
    private final CouponMemberService couponMemberService;
    private final OrderService orderService;

    @GetMapping("/products")
    public List<ProductResponseDto> orderProductList(
            @AuthenticationPrincipal UserDetailsDto user) {
            return cartService.getProductsFromCart(user.getMemberNo().toString());
    }

    @GetMapping("/coupons")
    public List<UnusedMemberCouponResponseDto> unusedMemberCouponList(
            @AuthenticationPrincipal UserDetailsDto user) {

        return couponMemberService.getUnusedMemberCoupons(user.getMemberNo());
    }

    @PostMapping
    public OrderResponseDto doOrder(@AuthenticationPrincipal UserDetailsDto user,
                                    @RequestBody OrderRegisterRequestDto requestDto) {

        return orderService.processOrder(user.getMemberNo(), requestDto);
    }
}
