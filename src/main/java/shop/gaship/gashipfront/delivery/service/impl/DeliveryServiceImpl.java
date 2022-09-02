package shop.gaship.gashipfront.delivery.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.delivery.adapter.DeliveryAdapter;
import shop.gaship.gashipfront.delivery.dto.response.DeliveryInfoStatusResponseDto;
import shop.gaship.gashipfront.delivery.dto.response.DeliveryLocationResponseDto;
import shop.gaship.gashipfront.delivery.dto.response.TrackingNoResponseDto;
import shop.gaship.gashipfront.delivery.service.DeliveryService;

/**
 * @author : 조재철
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryAdapter deliveryAdapter;

    @Override
    public void addTrackingNo(TrackingNoResponseDto trackingNoResponseDto) {
        deliveryAdapter.addTrackingNo(trackingNoResponseDto);
    }

    @Override
    public void changeDeliveryStatus(String orderNo, String status, LocalDateTime arrivalTime) {

        DeliveryInfoStatusResponseDto deliveryInfoStatusResponseDto = new DeliveryInfoStatusResponseDto();
        deliveryInfoStatusResponseDto.setOrderProductNo(orderNo);
        deliveryInfoStatusResponseDto.setStatus(status);
        deliveryInfoStatusResponseDto.setArrivalTime(arrivalTime);

        deliveryAdapter.changeDeliveryStatus(deliveryInfoStatusResponseDto);
    }

    @Override
    public List<DeliveryLocationResponseDto> findDeliveryLocationInfo(String trackingNo) {
        return deliveryAdapter.findDeliveryLocationInfo(trackingNo);
    }
}
