package shop.gaship.gashipfront.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이메일 인증번호가 담긴 클레스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCodeDto {
    private String verificationCode;
}
