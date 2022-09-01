package shop.gaship.gashipfront.delivery.adapter;

import java.util.List;
import shop.gaship.gashipfront.delivery.dto.response.DeliveryInfoStatusResponseDto;
import shop.gaship.gashipfront.delivery.dto.response.DeliveryLocationResponseDto;
import shop.gaship.gashipfront.delivery.dto.response.TrackingNoResponseDto;

/**
 * @author : 조재철
 * @since 1.0
 */
public interface DeliveryAdapter {

    void addTrackingNo(TrackingNoResponseDto trackingNoResponseDto);

    void changeDeliveryStatus(DeliveryInfoStatusResponseDto deliveryInfoStatusResponseDto);

    List<DeliveryLocationResponseDto> findDeliveryLocationInfo(String trackingNo);
}
