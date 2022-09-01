package shop.gaship.gashipfront.orderproduct.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

/**
 * @author : 조재철
 * @since 1.0
 */
@Getter
public class OrderProductResponseDto {
    private Integer orderNo;
    private Long totalOrderAmount;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDatetime;
    private String receiptName;
    private String receiptPhoneNumber;
}
