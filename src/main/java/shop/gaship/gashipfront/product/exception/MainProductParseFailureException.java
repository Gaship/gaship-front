package shop.gaship.gashipfront.product.exception;

/**
 * 메인페이지 상품을 파싱 실패 시 던지 예외.
 *
 * @author : 김보민
 * @since 1.0
 */
public class MainProductParseFailureException extends RuntimeException{
    public static final String message = "메인페이지 상품을 파싱하는데 실패했습니다.";

    public MainProductParseFailureException() {
        super(message);
    }
}
