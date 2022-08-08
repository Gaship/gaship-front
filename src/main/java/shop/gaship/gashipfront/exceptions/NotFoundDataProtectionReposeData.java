package shop.gaship.gashipfront.exceptions;

/**
 * NHN Secure Manager로부터 키가 수신 또는 예상치 못한 예와가 발생하였을 때 발생되는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class NotFoundDataProtectionReposeData extends RuntimeException {
    public NotFoundDataProtectionReposeData(String message) {
        super(message);
    }
}
