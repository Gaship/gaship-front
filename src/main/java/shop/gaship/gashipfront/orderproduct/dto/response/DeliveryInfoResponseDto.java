package shop.gaship.gashipfront.orderproduct.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * @author : 조재철
 * @since 1.0
 */
@Getter
public class DeliveryInfoResponseDto {

    private Status status;
    private LocalDateTime completionTime;
    private Long locationNo;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalTime;

}
