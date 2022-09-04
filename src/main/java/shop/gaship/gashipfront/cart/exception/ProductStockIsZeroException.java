package shop.gaship.gashipfront.cart.exception;

public class ProductStockIsZeroException extends RuntimeException {
    public static final String Message = "재고가 없는 상품입니다.";

    public ProductStockIsZeroException() {
        super(Message);
    }
}
