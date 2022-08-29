package shop.gaship.gashipfront.inquiry.controller.product;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryType.PRODUCT_INQUIRY;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;
import shop.gaship.gashipfront.inquiry.service.product.ProductInquiryService;
import shop.gaship.gashipfront.security.basic.dto.SignInSuccessUserDetailsDto;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 상품평에 대한 정보요청을 받는 rest 컨트롤러입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@RestController
@RequestMapping("/api/inquires")
@RequiredArgsConstructor
public class ProductInquiryRestController {
    private final ProductInquiryService productInquiryService;
    private final CommonInquiryService commonInquiryService;

    /**
     * 관리자 또는 회원이 상품상세페이지에서 해당하는 상품에 대한 상품문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable  페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @param productNo 기준이 되는 상품의 식별번호입니다.
     * @return 문의 목록을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/products/{productNo}")
    public PageResponse<InquiryListResponseDto> productInquiryProductList(
        Pageable pageable, @PathVariable Integer productNo) {

        return productInquiryService.findProductInquiriesByProductNo(pageable, productNo);
    }

    /**
     * 회원이 상품문의를 추가하기 위한 요청을 처리합니다.
     *
     * @param inquiryAddRequestDto 해당 server로 온 요청을 바인딩하기위한 DTO 객체입니다.
     * @author 최겸준
     */
    @PostMapping(value = "/product-inquiry")
    public void productInquiryAdd(@RequestBody @Valid InquiryAddRequestDto inquiryAddRequestDto,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails instanceof SignInSuccessUserDetailsDto) {
            inquiryAddRequestDto.setMemberNo(((SignInSuccessUserDetailsDto) userDetails)
                .getMemberNo().intValue());
        }
        if (userDetails instanceof UserDetailsDto) {
            inquiryAddRequestDto.setMemberNo(((UserDetailsDto) userDetails).getMemberNo());
        }
        inquiryAddRequestDto.setIsProduct(PRODUCT_INQUIRY.getValue());

        commonInquiryService.addInquiry(inquiryAddRequestDto);
    }
}
