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

    @GetMapping
    public String findOrderProductListByMemberNo(@PageableDefault Pageable pageable, Model model,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        PageResponse<OrderProductResponseDto> orderProductResponseDtoPageResponse =
            orderProductService.findOrderProductListByMemberNo(pageable, userDetailsDto.getMemberNo());

        model.addAttribute("orderProductList", orderProductResponseDtoPageResponse.getContent());

        model.addAttribute("next", orderProductResponseDtoPageResponse.isNext());
        model.addAttribute("previous", orderProductResponseDtoPageResponse.isPrevious());
        model.addAttribute("totalPage", orderProductResponseDtoPageResponse.getTotalPages());
        model.addAttribute("pageNum", orderProductResponseDtoPageResponse.getNumber() + 1);
        model.addAttribute("previousPageNo", pageable.getPageNumber() - 1);
        model.addAttribute("nextPageNo", pageable.getPageNumber() + 1);

        model.addAttribute("uri", "/member/order-product");

        return "orderproduct/member/orderProductList";
    }

    @GetMapping("/{orderProductNo}/details")
    public String findOrderProductOrderProductNo(@PathVariable(value = "orderProductNo") Integer orderProductNo,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto, Model model) {
        OrderProductDetailResponseDto orderProductDetailResponseDto =
            orderProductService.findOrderProductDetail(orderProductNo, userDetailsDto.getMemberNo());

        model.addAttribute("orderProductDetail", orderProductDetailResponseDto);

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
