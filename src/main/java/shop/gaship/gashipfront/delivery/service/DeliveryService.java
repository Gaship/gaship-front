package shop.gaship.gashipfront.delivery.service;

import java.time.LocalDateTime;
import java.util.List;
import shop.gaship.gashipfront.delivery.dto.response.DeliveryInfoStatusResponseDto;
import shop.gaship.gashipfront.delivery.dto.response.DeliveryLocationResponseDto;
import shop.gaship.gashipfront.delivery.dto.response.TrackingNoResponseDto;

/**
 * @author : 조재철
 * @since 1.0
 */
public interface DeliveryService {

    void addTrackingNo(TrackingNoResponseDto trackingNoResponseDto);

    void changeDeliveryStatus(String orderNo, String status, LocalDateTime arrivalTime);

    List<DeliveryLocationResponseDto> findDeliveryLocationInfo(String trackingNo);
}
