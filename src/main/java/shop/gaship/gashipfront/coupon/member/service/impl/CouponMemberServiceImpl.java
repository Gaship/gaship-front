package shop.gaship.gashipfront.coupon.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.coupon.member.adapter.CouponMemberAdapter;
import shop.gaship.gashipfront.coupon.member.dto.CouponGenerationIssueDto;
import shop.gaship.gashipfront.coupon.member.service.CouponMemberService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 조재철
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CouponMemberServiceImpl implements CouponMemberService {

    private final CouponMemberAdapter couponMemberAdapter;

    @Override
    public PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueByMemberNo(Pageable pageable,
        Integer memberNo) {
        return couponMemberAdapter.findCouponGenerationIssue(pageable, memberNo);
    }

    @Override
    public PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUsedByMemberNo(Pageable pageable,
        Integer memberNo) {
        return couponMemberAdapter.findCouponGenerationIssueUsed(pageable, memberNo);
    }

    @Override
    public PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnusedByMemberNo(Pageable pageable,
        Integer memberNo) {
        return couponMemberAdapter.findCouponGenerationIssueUnused(pageable, memberNo);
    }

    @Override
    public PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnusedExpiredByMemberNo(Pageable pageable,
        Integer memberNo) {
        return couponMemberAdapter.findCouponGenerationIssueUnusedExpired(pageable, memberNo);
    }

    @Override
    public PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnusedUnexpiredByMemberNo(Pageable pageable,
        Integer memberNo) {
        return couponMemberAdapter.findCouponGenerationIssueUnusedUnexpired(pageable, memberNo);
    }
}
