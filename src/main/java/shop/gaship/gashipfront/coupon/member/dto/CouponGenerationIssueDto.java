package shop.gaship.gashipfront.coupon.member.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class CouponGenerationIssueDto {

    @NotNull
    private Integer couponGenerationIssueNo;

    @NotBlank(message = "쿠폰 이름은 필수값 입니다.")
    private String name;

    @NotNull(message = "회원 번호는 필수값 입니다.")
    private Integer memberNo;

    private LocalDateTime expirationDatetime;
}
