package shop.gaship.gashipfront.exceptions;

/**
 * 응답에 대한 결과가 존재하지 않을 시 일으키는 예외입니다.
 *
 * @author : 김민수
 * @author 조재철
 * @since 1.0
 */
public class NoResponseDataException extends RuntimeException {

    /**
     * Instantiates a new No response data exception.
     *
     * @param message the message
     */
    public NoResponseDataException(String message) {
        super(message);
    }
}
