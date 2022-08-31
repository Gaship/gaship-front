package shop.gaship.gashipfront.orderproduct.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : 조재철
 * @since 1.0
 */
@Getter
public class OrderProductDetailResponseDto {
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
    private String filePath;
}
