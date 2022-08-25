package shop.gaship.gashipfront.renewalperiod.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 *
 * @author : 김세미
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RenewalPeriodResponseDto {
    private Integer statusCodeNo;
    private String explanation;
}
