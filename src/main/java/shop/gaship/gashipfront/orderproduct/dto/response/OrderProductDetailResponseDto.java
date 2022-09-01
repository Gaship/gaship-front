package shop.gaship.gashipfront.orderproduct.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : 조재철
 * @since 1.0
 */
@Getter
public class OrderProductDetailResponseDto {
    private Integer orderProductNo;
    private Integer productNo;
    private Integer orderNo;
    private String productName;
    private Long totalOrderAmount;
    private String orderProductStatus;
    private String trackingNo;
    private String color;
    private String manufacturer;
    private String manufacturerCountry;
    private String seller;
    private String importer;
    private String qualityAssuranceStandard;
    private String explanation;
    private Integer memberNo;
    private String address;
    private String zipCode;
    private String receiptName;
    private String receiptPhoneNumber;
    private String receiptSubPhoneNumber;
    private LocalDateTime cancellationDatetime;
    private Long cancellationAmount;
    private String cancellationReason;
    private String filePath;

    @Setter
    private Boolean existsReview;
}
