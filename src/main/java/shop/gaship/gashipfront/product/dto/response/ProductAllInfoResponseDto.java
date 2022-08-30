package shop.gaship.gashipfront.product.dto.response;


import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 상품을 가져올 정보들이 담겨있습니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
public class ProductAllInfoResponseDto {
    private Integer productNo;
    private String productName;
    private String productCode;
    private String categoryName;
    private Long amount;
    private LocalDateTime dateTime;
    private String manufacturer;
    private String country;
    private String seller;
    private String importer;
    private Long installationCost;
    private String quality;
    private String color;
    private Integer quantity;
    private String explanation;
    private Integer level;
    private String deliveryType;
    private String upperName;
    private List<String> tags;
    private List<String> filePaths;
}
