package shop.gaship.gashipfront.coupon.admin.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.coupon.admin.adapter.CouponAdminAdapter;
import shop.gaship.gashipfront.coupon.admin.service.CouponAdminService;
import shop.gaship.gashipfront.coupon.dto.CouponTypeCreationDto;
import shop.gaship.gashipfront.coupon.dto.CouponTypeDto;
import shop.gaship.gashipfront.coupon.dto.request.CouponGenerationIssueCreationConvertRequestDto;
import shop.gaship.gashipfront.coupon.dto.request.CouponGenerationIssueCreationRequestDto;
import shop.gaship.gashipfront.coupon.dto.response.CouponTargetMemberGradeResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 조재철
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CouponAdminServiceImpl implements CouponAdminService {

    private final CouponAdminAdapter couponAdminAdapter;

    @Override
    public void addCouponTypeFixedAmount(CouponTypeCreationDto couponTypeCreationDto) {
        couponAdminAdapter.addFixedAmountCouponType(couponTypeCreationDto);
    }

    @Override
    public void addCouponTypeFixedRate(CouponTypeCreationDto couponTypeCreationDto) {
        couponAdminAdapter.addFixedRateCouponType(couponTypeCreationDto);
    }

    @Override
    public void modifyRecommendMemberCoupon(CouponTypeCreationDto couponTypeCreationDto) {
        couponAdminAdapter.modifyRecommendMemberCoupon(couponTypeCreationDto);
    }

    @Override
    public void modifyCouponTypeStopGenerationIssue(Integer couponTypeNo) {
        couponAdminAdapter.modifyCouponTypeStopGenerationIssue(couponTypeNo);
    }

    @Override
    public void deleteCouponType(Integer couponTypeNo) {
        couponAdminAdapter.deleteCouponType(couponTypeNo);
    }

    @Override
    public PageResponse<CouponTypeDto> findCouponTypeList(Pageable pageable) {
        PageResponse<CouponTypeDto> couponTypeList = couponAdminAdapter.findCouponTypeList(pageable);

        return couponTypeList;
    }

    @Override
    public PageResponse<CouponTypeDto> findCouponTypeCanDeleteList(Pageable pageable) {
        PageResponse<CouponTypeDto> couponTypeCanDeleteList =
            couponAdminAdapter.findCouponTypeCanDeleteList(pageable);

        return couponTypeCanDeleteList;
    }

    @Override
    public PageResponse<CouponTypeDto> findCouponTypeCannotDeleteList(Pageable pageable) {
        PageResponse<CouponTypeDto> couponTypeCannotDeleteList =
            couponAdminAdapter.findCouponTypeCannotDeleteList(pageable);

        return couponTypeCannotDeleteList;
    }

    @Override
    public PageResponse<CouponTypeDto> findCouponTypeFixedAmountList(Pageable pageable) {
        PageResponse<CouponTypeDto> couponTypeFixedAmountList =
            couponAdminAdapter.findCouponTypeFixedAmountList(pageable);

        return couponTypeFixedAmountList;
    }

    @Override
    public PageResponse<CouponTypeDto> findCouponTypeFixedRateList(Pageable pageable) {
        PageResponse<CouponTypeDto> couponTypeFixedRateList =
            couponAdminAdapter.findCouponTypeFixedRateList(pageable);

        return couponTypeFixedRateList;
    }

    @Override
    public PageResponse<CouponTypeDto> findCouponTypeRecommend(Pageable pageable) {
        PageResponse<CouponTypeDto> couponTypeRecommendList =
            couponAdminAdapter.findCouponTypeRecommendList(pageable);

        return couponTypeRecommendList;
    }

    @Override
    public List<CouponTargetMemberGradeResponseDto> findCouponTargetMemberGrade() {
        return couponAdminAdapter.findCouponTargetMemberGrade();
    }

    @Override
    public void generateAndIssueCoupon(
        CouponGenerationIssueCreationRequestDto couponGenerationIssueCreationRequestDto) {

        CouponGenerationIssueCreationConvertRequestDto couponGenerationIssueCreationConvertRequestDto =
            new CouponGenerationIssueCreationConvertRequestDto();

        couponGenerationIssueCreationConvertRequestDto.convertCouponGenerationIssueCreationRequestDto(
            couponGenerationIssueCreationRequestDto);

        couponAdminAdapter.generateAndIssueCoupon(couponGenerationIssueCreationConvertRequestDto);
    }
}
