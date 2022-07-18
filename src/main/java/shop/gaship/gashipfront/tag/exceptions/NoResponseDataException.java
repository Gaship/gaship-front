package shop.gaship.gashipfront.tag.exceptions;

/**
 * packageName    : shop.gaship.gashipfront.exceptions <br/>
 * fileName       : NotFoundReposeData <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/11 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/11           김민수               최초 생성                         <br/>
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
