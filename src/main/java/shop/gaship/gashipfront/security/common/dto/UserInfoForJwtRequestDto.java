package shop.gaship.gashipfront.security.common.dto;

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
public class UserInfoForJwtRequestDto {
    private Integer memberNo;
    private List<String> authorities;
}