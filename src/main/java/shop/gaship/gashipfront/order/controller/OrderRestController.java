package shop.gaship.gashipfront.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.cart.dto.response.ProductResponseDto;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.coupon.member.dto.response.UnusedMemberCouponResponseDto;
import shop.gaship.gashipfront.coupon.member.service.CouponMemberService;
import shop.gaship.gashipfront.security.basic.dto.SignInSuccessUserDetailsDto;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;

import java.util.List;

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
            @AuthenticationPrincipal UserDetails user) {
        if(user instanceof UserDetailsDto) {
            return cartService.getProductsFromCart(((UserDetailsDto) user).getMemberNo().toString());
        }
        return cartService.getProductsFromCart(((SignInSuccessUserDetailsDto) user).getMemberNo().toString());
    }

    @GetMapping("/coupons")
    public List<UnusedMemberCouponResponseDto> unusedMemberCouponList(
            @AuthenticationPrincipal UserDetails user) {
        Integer memberNo;

        if(user instanceof UserDetailsDto) {
            memberNo = ((UserDetailsDto) user).getMemberNo();
        } else {
            memberNo = ((SignInSuccessUserDetailsDto) user).getMemberNo().intValue();
        }

        return couponMemberService.getUnusedMemberCoupons(memberNo);
    }
}
