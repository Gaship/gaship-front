package shop.gaship.gashipfront.order.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 주문 요청을 위한 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class OrderRegisterRequestDto {
    private Integer addressListNo;
    private Integer memberNo;
    private List<OrderProductSpecificDto> orderProducts;
    private String receiverName;
    private String receiverPhoneNo;
    private String receiverSubPhoneNo;
    private String deliveryRequest;
    private Long totalAmount;
}
