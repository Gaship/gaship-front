package shop.gaship.gashipfront.coupon.member.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 현재 사용가능한 회원의 쿠폰 조회에 대한 응답 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public class UnusedMemberCouponResponseDto {
    private Integer couponGenerationIssueNo;
    private String couponName;
    private Long discountAmount;
    private Integer discountRate;
    private LocalDateTime issueDatetime;
    private LocalDateTime expirationDatetime;
}
