package shop.gaship.gashipfront.coupon.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : 조재철
 * @since 1.0
 */

@Getter
@AllArgsConstructor
public class CouponGenerationIssueCreationRequestDto {

    @NotNull
    @Min(1)
    private Integer couponTypeNo;

    @NotNull
    @Min(1)
    private Integer memberGradeNo;

    @NotNull
    private String generationDatetime;

    private String issueDatetime;

    @NotNull
    private String expirationDatetime;

}
