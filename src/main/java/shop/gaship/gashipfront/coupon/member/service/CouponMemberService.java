package shop.gaship.gashipfront.coupon.member.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.coupon.member.dto.CouponGenerationIssueDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 조재철
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
}
