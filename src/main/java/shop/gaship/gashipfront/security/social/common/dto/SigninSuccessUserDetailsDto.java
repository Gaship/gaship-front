package shop.gaship.gashipfront.security.social.common.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 성공시 반환받을 때 사용하는 dto입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Getter
@Setter
public class SigninSuccessUserDetailsDto {
    private Integer identifyNo;
    private List<String> authorities;
}