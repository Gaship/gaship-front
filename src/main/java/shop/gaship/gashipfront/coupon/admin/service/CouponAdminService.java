package shop.gaship.gashipfront.coupon.admin.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.coupon.dto.CouponTypeDto;
import shop.gaship.gashipfront.coupon.dto.CouponTypeCreationDto;
import shop.gaship.gashipfront.coupon.dto.request.CouponGenerationIssueCreationRequestDto;
import shop.gaship.gashipfront.coupon.dto.response.CouponTargetMemberGradeResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 조재철
 * @since 1.0
 */
public interface CouponAdminService {

    void addCouponTypeFixedAmount(CouponTypeCreationDto couponTypeCreationDto);

    void addCouponTypeFixedRate(CouponTypeCreationDto couponTypeCreationDto);

    void modifyRecommendMemberCoupon(CouponTypeCreationDto couponTypeCreationDto);

    void modifyCouponTypeStopGenerationIssue(Integer couponTypeNo);

    void deleteCouponType(Integer couponTypeNo);

    PageResponse<CouponTypeDto> findCouponTypeList(Pageable pageable);

    PageResponse<CouponTypeDto> findCouponTypeCanDeleteList(Pageable pageable);

    PageResponse<CouponTypeDto> findCouponTypeCannotDeleteList(Pageable pageable);

    PageResponse<CouponTypeDto> findCouponTypeFixedAmountList(Pageable pageable);

    PageResponse<CouponTypeDto> findCouponTypeFixedRateList(Pageable pageable);

    PageResponse<CouponTypeDto> findCouponTypeRecommend(Pageable pageable);

    List<CouponTargetMemberGradeResponseDto> findCouponTargetMemberGrade();

    void generateAndIssueCoupon(CouponGenerationIssueCreationRequestDto couponGenerationIssueCreationRequestDto);
}
