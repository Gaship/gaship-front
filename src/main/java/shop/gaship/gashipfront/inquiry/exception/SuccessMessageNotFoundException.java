package shop.gaship.gashipfront.inquiry.exception;

/**
 * @author : 최겸준
 * @since 1.0
 */
public class SuccessMessageNotFoundException extends RuntimeException {
    public static final String MESSAGE = "성공한 메세지를 session에서 찾을 수 없습니다.";

    public SuccessMessageNotFoundException() {
        super(MESSAGE);
    }
}
