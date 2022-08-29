package shop.gaship.gashipfront.totalsale.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : 조재철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TotalSaleResponseDto {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime totalSaleDate;
    private Long orderCnt;
    private Long orderCancelCnt;
    private Long orderSaleCnt;
    private Long totalAmount;
    private Long cancelAmount;
    private Long orderSaleAmount;
}
