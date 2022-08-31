package shop.gaship.gashipfront.coupon.member.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.coupon.member.dto.CouponGenerationIssueDto;
import shop.gaship.gashipfront.coupon.member.dto.response.UnusedMemberCouponResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

import java.util.List;

/**
 * @author : 조재철
 * @author : 김세미
 * @since 1.0
 */
public interface CouponMemberService {

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueByMemberNo(Pageable pageable,
        Integer memberNo);

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUsedByMemberNo(Pageable pageable,
        Integer memberNo);

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnusedByMemberNo(Pageable pageable,
        Integer memberNo);

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnusedExpiredByMemberNo(Pageable pageable,
        Integer memberNo);

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnusedUnexpiredByMemberNo(Pageable pageable,
        Integer memberNo);

    List<UnusedMemberCouponResponseDto> getUnusedMemberCoupons(Integer memberNo);
}
