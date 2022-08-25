package shop.gaship.gashipfront.security.basic.dto;

import java.io.Serializable;
import java.util.Collection;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 조재철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequestDto implements Serializable {

    @NotNull(message = "회원번호는 필수 입니다.")
    private Integer memberNo;

    @NotBlank(message = "이메일은 필수 정보 입니다.")
    private String email;

    @Size(min = 1, max = 1, message = "하나의 권한만 가져야 합니다.")
    private Collection<String> authorities;
}
