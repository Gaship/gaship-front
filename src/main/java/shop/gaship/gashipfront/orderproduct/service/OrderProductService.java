package shop.gaship.gashipfront.orderproduct.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.orderproduct.dto.response.DeliveryInfoResponseDto;
import shop.gaship.gashipfront.orderproduct.dto.response.OrderProductDetailResponseDto;
import shop.gaship.gashipfront.orderproduct.dto.response.OrderProductResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 조재철
 * @since 1.0
 */
public interface OrderProductService {

    PageResponse<OrderProductResponseDto> findOrderProductListByMemberNo(
        Pageable pageable, Integer memberNo);

    List<DeliveryInfoResponseDto> findDeliveryInfoByTrackingNo(String trackingNo);

    PageResponse<OrderProductDetailResponseDto> findOrderProductDetail(Integer orderProductNo, Integer memberNo, Pageable pageable);
}
