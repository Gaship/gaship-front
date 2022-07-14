package shop.gaship.gashipfront.security.social.exception;

/**
 * packageName    : shop.gaship.gashipfront.security.social.exception
 * fileName       : CsrfProtectedException
 * author         : choi-gyeom-jun
 * date           : 2022-07-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-14        choi-gyeom-jun       최초 생성
 */
public class CsrfProtectedException extends RuntimeException {
    public CsrfProtectedException(String message) {
        super(message);
    }
}
