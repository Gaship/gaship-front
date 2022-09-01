package shop.gaship.gashipfront.delivery.controller;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.delivery.dto.response.DeliveryLocationResponseDto;
import shop.gaship.gashipfront.delivery.dto.response.TrackingNoResponseDto;
import shop.gaship.gashipfront.delivery.service.DeliveryService;

/**
 * @author : 조재철
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * 배송 서버에서 운송장번호를 생성후 전달하는데 그 요청을 받아서 처리하는 메서드 입니다.
     *
     * @param trackingNo     운송장 번호
     * @param orderProductNo 주문 상품 번호
     * @return 요청에 대해 잘 처리 했다는 응답입니다.
     */
    @GetMapping(value = "/eggplant/tracking-no/{trackingNo}")
    public ResponseEntity<Void> addTrackingNo(@PathVariable(value = "trackingNo") String trackingNo,
        @RequestParam(value = "orderNo") String orderProductNo) {

        TrackingNoResponseDto trackingNoResponseDto = new TrackingNoResponseDto();
        trackingNoResponseDto.setTrackingNo(trackingNo);
        trackingNoResponseDto.setOrderProductNo(orderProductNo);

        deliveryService.addTrackingNo(trackingNoResponseDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 배송 상태가 변경 되었을 때 쇼핑몰 서버에도 배송상태 변경 반영 요청에 대해 처리하는 메서드 입니다.
     *
     * @param orderNo 주문 상품 번호.
     * @param status 배송 상태.
     * @param arrivalTime 위치 도착 시간.
     * @return 요청을 잘 처리했다는 응답 입니다.
     */
    @GetMapping(value = "/eggplant/delivery-info")
    public ResponseEntity<Void> changeDeliveryStatus(@RequestParam(value = "orderNo") String orderNo,
        @RequestParam(value = "status") String status,
        @RequestParam(value = "arrivalTime", required = false) @DateTimeFormat(iso = ISO.DATE_TIME)
            LocalDateTime arrivalTime) {
        deliveryService.changeDeliveryStatus(orderNo, status, arrivalTime);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 운송장 번호를 통해 배송 위치 정보를 요청 합니다.
     *
     * @param trackingNo 운송장 번호.
     * @return 요청에 대해 잘 처리했다는 응답.
     */
    @GetMapping(value = "/eggplant/delivery/location-status")
    public ResponseEntity<List<DeliveryLocationResponseDto>> findDeliveryLocationInfo(@RequestParam(value = "trackingNo") String trackingNo) {

        return ResponseEntity.ok(deliveryService.findDeliveryLocationInfo(trackingNo));
    }
}
