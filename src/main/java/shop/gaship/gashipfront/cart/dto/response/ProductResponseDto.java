package shop.gaship.gashipfront.cart.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * 장바구니 조회시에 페이지에 담길 객체입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
@Builder
public class ProductResponseDto {
    private Integer productNo;
    private String productName;
    private Long amount;
    private Long installationCost;
    private Integer orderQuantity;
    private Integer quantity;
    private String filePaths;

    public ProductResponseDto changeQuantityToStock() {
        this.orderQuantity = Math.min(orderQuantity,quantity);
        return this;
    }
}
