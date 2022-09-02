package shop.gaship.gashipfront.delivery.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : 조재철
 * @since 1.0
 */
@Getter
@Setter
public class TrackingNoResponseDto {

    private String trackingNo;

    @JsonProperty(value = "orderNo")
    private String orderProductNo;
}
