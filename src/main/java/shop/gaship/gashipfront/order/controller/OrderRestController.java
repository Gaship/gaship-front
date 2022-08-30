package shop.gaship.gashipfront.order.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.cart.dto.response.ProductResponseDto;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.coupon.member.dto.CouponGenerationIssueDto;
import shop.gaship.gashipfront.coupon.member.service.CouponMemberService;
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

    @GetMapping("/products")
    public List<ProductResponseDto> orderProductList(
        @AuthenticationPrincipal UserDetailsDto user) {
        return cartService.getProductsFromCart(user.getMemberNo().toString());
    }

    @GetMapping("/coupons")
    public List<CouponGenerationIssueDto> unusedMemberCouponList(
        @AuthenticationPrincipal UserDetailsDto user) {
        return couponMemberService
            .findCouponGenerationIssueUnusedUnexpiredByMemberNo(
                PageRequest.of(0, Integer.MAX_VALUE), user.getMemberNo())
            .getContent();
    }
}
