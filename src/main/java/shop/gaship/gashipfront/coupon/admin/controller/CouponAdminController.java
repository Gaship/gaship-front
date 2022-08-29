package shop.gaship.gashipfront.coupon.admin.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.gaship.gashipfront.coupon.admin.service.CouponAdminService;
import shop.gaship.gashipfront.coupon.dto.CouponTypeCreationDto;
import shop.gaship.gashipfront.coupon.dto.CouponTypeDto;
import shop.gaship.gashipfront.coupon.dto.group.FixAmountGroup;
import shop.gaship.gashipfront.coupon.dto.group.FixRateGroup;
import shop.gaship.gashipfront.coupon.dto.request.CouponGenerationIssueCreationRequestDto;
import shop.gaship.gashipfront.coupon.dto.response.CouponTargetMemberGradeResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 조재철
 * @since 1.0
 */

@RequestMapping("/admin/coupons")
@Controller
@RequiredArgsConstructor
@Slf4j
public class CouponAdminController {

    private final CouponAdminService couponAdminService;

    @GetMapping("/coupon-type/fixed-amount/add")
    public String fixedAmountCouponTypeAdd() {
        return "coupon/admin/addFixedAmountCouponType";
    }

    @PostMapping("/coupon-type/fixed-amount/add")
    public String fixedAmountCouponTypeAdd(
        @Validated(FixAmountGroup.class) CouponTypeCreationDto couponTypeCreationDto) {
        couponAdminService.addCouponTypeFixedAmount(couponTypeCreationDto);

        return "redirect:/admin/coupons/coupon-type";
    }

    @GetMapping("/coupon-type/fixed-rate/add")
    public String fixedRateCouponTypeAdd() {
        return "coupon/admin/addFixedRateCouponType";
    }

    @PostMapping("/coupon-type/fixed-rate/add")
    public String fixedRateCouponTypeAdd(
        @Validated(FixRateGroup.class) CouponTypeCreationDto couponTypeCreationDto) {
        couponAdminService.addCouponTypeFixedRate(couponTypeCreationDto);

        return "redirect:/admin/coupons/coupon-type";
    }

    @GetMapping("/coupon-type/recommend-member-coupon")
    public String recommendCouponTypeModify() {
        return "coupon/admin/modifyRecommendCouponType";
    }

    @PutMapping("/coupon-type/recommend-member-coupon")
    public String recommendMemberCouponTypeModify(@Valid CouponTypeCreationDto couponTypeCreationDto) {
        couponAdminService.modifyRecommendMemberCoupon(couponTypeCreationDto);

        return "redirect:/admin/coupons/coupon-type";
    }

    @PatchMapping("/coupon-type/{couponTypeNo}/stop-generation-issue")
    public String modifyCouponTypeStopGenerationIssue(@PathVariable(value = "couponTypeNo") Integer couponTypeNo) {
        couponAdminService.modifyCouponTypeStopGenerationIssue(couponTypeNo);

        return "coupon/admin/couponTypeList";
    }

    @DeleteMapping("/coupon-type/{couponTypeNo}")
    public void couponTypeDelete(@PathVariable(value = "couponTypeNo") Integer couponTypeNo) {
        couponAdminService.deleteCouponType(couponTypeNo);
    }

    @GetMapping("/coupon-type")
    public String couponTypeList(Pageable pageable, Model model) {
        PageResponse<CouponTypeDto> couponTypeDtoPageResponse = couponAdminService.findCouponTypeList(pageable);

        model.addAttribute("couponTypeList", couponTypeDtoPageResponse.getContent());

        return "coupon/admin/couponTypeList";
    }

    @GetMapping("/coupon-type/delete-can")
    public String couponTypeCanDeleteList(Pageable pageable, Model model) {
        PageResponse<CouponTypeDto> couponTypeDtoPageResponse =
            couponAdminService.findCouponTypeCanDeleteList(pageable);

        model.addAttribute("couponTypeList", couponTypeDtoPageResponse.getContent());

        return "coupon/admin/couponTypeDeleteList";
    }

    @GetMapping("/coupon-type/delete-cannot")
    public String couponTypeCannotDeleteList(Pageable pageable, Model model) {
        PageResponse<CouponTypeDto> couponTypeDtoPageResponse =
            couponAdminService.findCouponTypeCannotDeleteList(pageable);

        model.addAttribute("couponTypeList", couponTypeDtoPageResponse.getContent());

        return "coupon/admin/couponTypeList";
    }

    @GetMapping("/coupon-type/fixed-amount")
    public String couponTypeFixedAmountList(Pageable pageable, Model model) {
        PageResponse<CouponTypeDto> couponTypeDtoPageResponse =
            couponAdminService.findCouponTypeFixedAmountList(pageable);

        model.addAttribute("couponTypeList", couponTypeDtoPageResponse.getContent());

        return "coupon/admin/couponTypeList";
    }

    @GetMapping("/coupon-type/fixed-rate")
    public String couponTypeFixedRateList(Pageable pageable, Model model) {
        PageResponse<CouponTypeDto> couponTypeDtoPageResponse =
            couponAdminService.findCouponTypeFixedRateList(pageable);

        model.addAttribute("couponTypeList", couponTypeDtoPageResponse.getContent());

        return "coupon/admin/couponTypeList";
    }

    @GetMapping("/coupon-type/recommend")
    public String couponTypeRecommendList(Pageable pageable, Model model) {
        PageResponse<CouponTypeDto> couponTypeDtoPageResponse =
            couponAdminService.findCouponTypeRecommend(pageable);

        model.addAttribute("couponTypeList", couponTypeDtoPageResponse.getContent());

        return "coupon/admin/couponTypeList";
    }

    @GetMapping("/coupon-type/{couponTypeNo}/generation-coupon")
    public String couponGeneration(@PathVariable(value = "couponTypeNo") Integer couponTypeNo, Model model) {

        List<CouponTargetMemberGradeResponseDto> couponTargetMemberGradeResponseDtoList =
            couponAdminService.findCouponTargetMemberGrade();

        model.addAttribute("couponTypeNo", couponTypeNo);
        model.addAttribute("memberGradeList", couponTargetMemberGradeResponseDtoList);

        return "coupon/admin/couponTypeGeneration";
    }

    @PostMapping(value = "/coupon-type/generation-coupon/issue-reservation")
    public String couponIssueReservation(
        CouponGenerationIssueCreationRequestDto couponGenerationIssueCreationRequestDto) {

        couponAdminService.generateAndIssueCoupon(couponGenerationIssueCreationRequestDto);

        return "redirect:/admin/coupons/coupon-type";
    }

    @GetMapping("/coupon-type/{couponTypeNo}/issue-coupon")
    public String couponIssue(@PathVariable(value = "couponTypeNo") Integer couponTypeNo, Model model) {

        List<CouponTargetMemberGradeResponseDto> couponTargetMemberGradeResponseDtoList =
            couponAdminService.findCouponTargetMemberGrade();

        model.addAttribute("couponTypeNo", couponTypeNo);
        model.addAttribute("memberGradeList", couponTargetMemberGradeResponseDtoList);

        return "coupon/admin/couponTypeIssue";
    }

    @PostMapping(value = "/coupon-type/generation-coupon/issue")
    public String couponDirectIssue(
        CouponGenerationIssueCreationRequestDto couponGenerationIssueCreationRequestDto) {

        couponAdminService.generateAndIssueCoupon(couponGenerationIssueCreationRequestDto);

        return "redirect:/admin/coupons/coupon-type";
    }

}
