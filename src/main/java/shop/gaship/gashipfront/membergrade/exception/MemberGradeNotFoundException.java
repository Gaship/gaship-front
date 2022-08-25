package shop.gaship.gashipfront.membergrade.exception;

/**
 * 조회하려고 하는 회원등급이 없는 경우 발생하는 exception 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public class MemberGradeNotFoundException extends RuntimeException {
    public static final String MESSAGE = "회원등급을 찾을 수 없습니다.";

    public MemberGradeNotFoundException() {
        super(MESSAGE);
    }
}
