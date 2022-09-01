package shop.gaship.gashipfront.orderproduct.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.coupon.member.dto.CouponGenerationIssueDto;
import shop.gaship.gashipfront.orderproduct.adapter.OrderProductAdapter;
import shop.gaship.gashipfront.orderproduct.dto.response.DeliveryInfoResponseDto;
import shop.gaship.gashipfront.orderproduct.dto.response.OrderProductDetailResponseDto;
import shop.gaship.gashipfront.orderproduct.dto.response.OrderProductResponseDto;
import shop.gaship.gashipfront.orderproduct.service.OrderProductService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 조재철
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductAdapter orderProductAdapter;

    @Override
    public PageResponse<OrderProductResponseDto> findOrderProductListByMemberNo(
        Pageable pageable, Integer memberNo) {
        return orderProductAdapter.findOrderProductListByMemberNo(pageable, memberNo);
    }

    @Override
    public List<DeliveryInfoResponseDto> findDeliveryInfoByTrackingNo(String trackingNo) {
        return orderProductAdapter.findDeliveryInfoByTrackingNo(trackingNo);
    }

    @Override
    public PageResponse<OrderProductDetailResponseDto> findOrderProductDetail(Integer orderProductNo, Integer memberNo, Pageable pageable) {
        return orderProductAdapter.findOrderProductDetail(orderProductNo, memberNo, pageable);
    }
}
