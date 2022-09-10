package shop.gaship.gashipfront.interceptor.exception;

/**
 * 토큰 발급에 요청의 예외가 발생시 해당 예외를 발생시킵니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class TokenResponseException extends RuntimeException {
    private static final String MESSAGE = "토큰 발급의 응답에 예외가 발생했습니다.";

    public TokenResponseException() {
        super(MESSAGE);
    }
}
