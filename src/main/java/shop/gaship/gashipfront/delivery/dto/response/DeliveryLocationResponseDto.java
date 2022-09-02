package shop.gaship.gashipfront.delivery.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * @author : 조재철
 * @since 1.0
 */
@Getter
public class DeliveryLocationResponseDto {

    private String status;
    private LocalDateTime completionTime;
    private String middleLocation;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalTime;
}
