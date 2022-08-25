package shop.gaship.gashipfront.coupon.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipfront.coupon.dto.group.FixAmountGroup;
import shop.gaship.gashipfront.coupon.dto.group.FixRateGroup;

/**
 * @author : 조재철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CouponTypeDto {

    @NotNull
    private Integer couponTypeNo;

    @NotBlank
    private String name;

    @Min(value = 0, message = "할인 비율은 0 ~ 100% 입니다.", groups = FixRateGroup.class)
    @Max(value = 0, message = "할인 비율은 0 ~ 100% 입니다.", groups = FixRateGroup.class)
    private Integer discountRate;

    @Min(value = 0, message = "할인금액은 0원 이상입니다.", groups = FixAmountGroup.class)
    private Long discountAmount;

    @NotNull
    private Boolean isStopGenerationIssue;
}

