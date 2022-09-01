package shop.gaship.gashipfront.orderproduct.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.gaship.gashipfront.orderproduct.dto.response.DeliveryInfoResponseDto;
import shop.gaship.gashipfront.orderproduct.dto.response.OrderProductDetailResponseDto;
import shop.gaship.gashipfront.orderproduct.dto.response.OrderProductResponseDto;
import shop.gaship.gashipfront.orderproduct.service.OrderProductService;
import shop.gaship.gashipfront.productreview.service.ProductReviewService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 조재철
 * @since 1.0
 */
@RequiredArgsConstructor
@RequestMapping("/member/order-product")
@Controller
public class OrderProductController {

    private final OrderProductService orderProductService;
    private final ProductReviewService productReviewService;

    @GetMapping
    public String findOrderProductListByMemberNo(@PageableDefault Pageable pageable, Model model,
                                                 @AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        PageResponse<OrderProductResponseDto> orderProductResponseDtoPageResponse =
            orderProductService.findOrderProductListByMemberNo(pageable, userDetailsDto.getMemberNo());

        List<OrderProductResponseDto> content = orderProductResponseDtoPageResponse.getContent();
//        content.forEach(orderProduct -> orderProduct.setExistsReview(
//            productReviewService.isExist(orderProduct.getOrderProductNo())));

        model.addAttribute("orderProductList", content);

        model.addAttribute("next", orderProductResponseDtoPageResponse.isNext());
        model.addAttribute("previous", orderProductResponseDtoPageResponse.isPrevious());
        model.addAttribute("totalPage", orderProductResponseDtoPageResponse.getTotalPages());
        model.addAttribute("pageNum", orderProductResponseDtoPageResponse.getNumber() + 1);
        model.addAttribute("previousPageNo", pageable.getPageNumber() - 1);
        model.addAttribute("nextPageNo", pageable.getPageNumber() + 1);

        model.addAttribute("uri", "/member/order-product");

        return "orderproduct/member/orderProductList";
    }

    @GetMapping("/{orderNo}/details")
    public String findOrderProductOrderProductNo(@PathVariable(value = "orderNo") Integer orderNo,
                                                 @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                                 @PageableDefault Pageable pageable,
                                                 Model model) {
        PageResponse<OrderProductDetailResponseDto> orderProductDetailResponseDto =
            orderProductService.findOrderProductDetail(orderNo, userDetailsDto.getMemberNo(),pageable);

        model.addAttribute("next", orderProductDetailResponseDto.isNext());
        model.addAttribute("previous", orderProductDetailResponseDto.isPrevious());
        model.addAttribute("totalPage", orderProductDetailResponseDto.getTotalPages());
        model.addAttribute("pageNum", orderProductDetailResponseDto.getNumber() + 1);
        model.addAttribute("previousPageNo", pageable.getPageNumber() - 1);
        model.addAttribute("nextPageNo", pageable.getPageNumber() + 1);

        model.addAttribute("uri", "/member/order-product/" + orderNo +"/details");

        model.addAttribute("orderProductDetail", orderProductDetailResponseDto.getContent());

        return "orderproduct/member/orderProductDetail";
    }

    @GetMapping("/delivery-info/tracking-no/{trackingNo}")
    public String findDeliveryInfoByTrackingNo(@PathVariable(value = "trackingNo") String trackingNo, Model model) {
        List<DeliveryInfoResponseDto> deliveryInfoResponseDtoList =
            orderProductService.findDeliveryInfoByTrackingNo(trackingNo);

        model.addAttribute("deliveryInfoList", deliveryInfoResponseDtoList);

        return "orderproduct/member/deliveryInfo";
    }

}
