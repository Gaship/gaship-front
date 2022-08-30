package shop.gaship.gashipfront.security.basic.dto;

import java.util.Collection;
import lombok.Getter;

/**
 * 로그인 요청시 받아오는 유저 정보 dto 클래스.
 *
 * @author : 조재철
 * @since 1.0
 */
@Getter
public class SignInUserDetailsDto {

    private Integer memberNo;
    private String email;
    private String hashedPassword;
    private Boolean isSocial;
    private Collection<String> authorities;
}
