package shop.gaship.gashipfront.cart.exception;

public class ProductStockIsLessThanOrderQuantity extends RuntimeException {
    public static final String MESSAGE = "재고보다 많은 수량을 담을 수 없습니다.";

    public ProductStockIsLessThanOrderQuantity() {
        super(MESSAGE);
    }
}
