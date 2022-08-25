package shop.gaship.gashipfront.coupon.member.adapter;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.coupon.member.dto.CouponGenerationIssueDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 조재철
 * @since 1.0
 */
public interface CouponMemberAdapter {

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssue(Pageable pageable, Integer memberNo);

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUsed(Pageable pageable, Integer memberNo);

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnused(Pageable pageable,
        Integer memberNo);

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnusedExpired(Pageable pageable,
        Integer memberNo);

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnusedUnexpired(Pageable pageable,
        Integer memberNo);
}
