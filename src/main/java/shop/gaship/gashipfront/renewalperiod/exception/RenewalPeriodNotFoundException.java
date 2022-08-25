package shop.gaship.gashipfront.renewalperiod.exception;

/**
 *
 *
 * @author : 김세미
 * @since 1.0
 */
public class RenewalPeriodNotFoundException extends RuntimeException {
    public static final String MESSAGE = "갱신기간을 찾을 수 없습니다.";

    public RenewalPeriodNotFoundException() {
        super(MESSAGE);
    }
}
