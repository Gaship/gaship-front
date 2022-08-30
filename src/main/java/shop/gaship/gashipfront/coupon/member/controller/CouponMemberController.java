package shop.gaship.gashipfront.coupon.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.gaship.gashipfront.coupon.member.dto.CouponGenerationIssueDto;
import shop.gaship.gashipfront.coupon.member.service.CouponMemberService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 조재철
 * @since 1.0
 */

@RequestMapping("/member/coupons")
@Controller
@RequiredArgsConstructor
public class CouponMemberController {

    private final CouponMemberService couponMemberService;

    @ModelAttribute("user")
    public Integer getMemberNo(@AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        return userDetailsDto.getMemberNo();
    }

    @GetMapping("/coupon-generation-issues/member")
    public String couponGenerationIssueByMemberNoList(Pageable pageable, Model model,
        @ModelAttribute("user") Integer memberNo) {
        PageResponse<CouponGenerationIssueDto> couponTypeDtoPageResponse =
            couponMemberService.findCouponGenerationIssueByMemberNo(pageable, memberNo);

        model.addAttribute("memberNo", memberNo);

        pagingProcessing(model, couponTypeDtoPageResponse, pageable);

        model.addAttribute("uri", "/member/coupons/coupon-generation-issue/member");

        return "coupon/member/couponGenerationIssueList";
    }

    @GetMapping("/coupon-generation-issues/member/used-coupons")
    public String couponGenerationIssueUsedByMemberNoList(Pageable pageable, Model model,
        @ModelAttribute("user") Integer memberNo) {
        PageResponse<CouponGenerationIssueDto> couponTypeDtoPageResponse =
            couponMemberService.findCouponGenerationIssueUsedByMemberNo(pageable, memberNo);

        model.addAttribute("memberNo", memberNo);

        pagingProcessing(model, couponTypeDtoPageResponse, pageable);

        model.addAttribute("uri", "/member/coupons/coupon-generation-issue/member/used-coupons");

        return "coupon/member/couponGenerationIssueList";
    }

    @GetMapping("/coupon-generation-issues/member/unused-coupons")
    public String couponGenerationIssueUnusedByMemberNoList(Pageable pageable, Model model,
        @ModelAttribute("user") Integer memberNo) {
        PageResponse<CouponGenerationIssueDto> couponTypeDtoPageResponse =
            couponMemberService.findCouponGenerationIssueUnusedByMemberNo(pageable, memberNo);

        model.addAttribute("memberNo", memberNo);

        pagingProcessing(model, couponTypeDtoPageResponse, pageable);

        model.addAttribute("uri", "/member/coupons/coupon-generation-issue/member/unused-coupons");

        return "coupon/member/couponGenerationIssueList";
    }

    @GetMapping("/coupon-generation-issues/member/unused-coupons/expired-coupons")
    public String couponGenerationIssueUnusedExpiredByMemberNoList(Pageable pageable, Model model,
        @ModelAttribute("user") Integer memberNo) {
        PageResponse<CouponGenerationIssueDto> couponTypeDtoPageResponse =
            couponMemberService.findCouponGenerationIssueUnusedExpiredByMemberNo(pageable, memberNo);

        model.addAttribute("memberNo", memberNo);

        pagingProcessing(model, couponTypeDtoPageResponse, pageable);

        model.addAttribute("uri", "/member/coupons/coupon-generation-issue/member/unused-coupons/expired-coupons");

        return "coupon/member/couponGenerationIssueList";
    }

    @GetMapping("/coupon-generation-issues/member/unused-coupons/unexpired-coupons")
    public String couponGenerationIssueUnusedUnexpiredByMemberNoList(Pageable pageable, Model model,
        @ModelAttribute("user") Integer memberNo) {
        PageResponse<CouponGenerationIssueDto> couponTypeDtoPageResponse =
            couponMemberService.findCouponGenerationIssueUnusedUnexpiredByMemberNo(pageable, memberNo);

        model.addAttribute("memberNo", memberNo);

        pagingProcessing(model, couponTypeDtoPageResponse, pageable);

        model.addAttribute("uri", "/member/coupons/coupon-generation-issue/member/unused-coupons/unexpired-coupons");

        return "coupon/member/couponGenerationIssueList";
    }

    private void pagingProcessing(Model model, PageResponse<CouponGenerationIssueDto> couponTypeDtoPageResponse,
        Pageable pageable) {
        model.addAttribute("couponTypeList", couponTypeDtoPageResponse.getContent());

        model.addAttribute("next", couponTypeDtoPageResponse.isNext());
        model.addAttribute("previous", couponTypeDtoPageResponse.isPrevious());
        model.addAttribute("totalPage", couponTypeDtoPageResponse.getTotalPages());
        model.addAttribute("pageNum", couponTypeDtoPageResponse.getNumber() + 1);
        model.addAttribute("previousPageNo", pageable.getPageNumber() - 1);
        model.addAttribute("nextPageNo", pageable.getPageNumber() + 1);
    }
}
