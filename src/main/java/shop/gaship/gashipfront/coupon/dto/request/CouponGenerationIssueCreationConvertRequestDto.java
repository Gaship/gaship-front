package shop.gaship.gashipfront.coupon.dto.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author : 조재철
 * @since 1.0
 */
@Getter
public class CouponGenerationIssueCreationConvertRequestDto {

    @NotNull
    @Min(1)
    private Integer couponTypeNo;

    @NotNull
    @Min(1)
    private Integer memberGradeNo;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime generationDatetime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime issueDatetime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expirationDatetime;


    public void convertCouponGenerationIssueCreationRequestDto(
        CouponGenerationIssueCreationRequestDto couponGenerationIssueCreationRequestDto) {
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        this.couponTypeNo = couponGenerationIssueCreationRequestDto.getCouponTypeNo();
        this.memberGradeNo = couponGenerationIssueCreationRequestDto.getMemberGradeNo();
        this.generationDatetime =
            LocalDateTime.parse(couponGenerationIssueCreationRequestDto.getGenerationDatetime(), formatter);
        this.issueDatetime =
            LocalDateTime.parse(couponGenerationIssueCreationRequestDto.getIssueDatetime(), formatter);
        this.expirationDatetime =
            LocalDateTime.parse(couponGenerationIssueCreationRequestDto.getExpirationDatetime(), formatter);

    }
}
