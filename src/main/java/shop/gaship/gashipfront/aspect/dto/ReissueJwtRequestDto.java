package shop.gaship.gashipfront.aspect.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : 조재철
 * @since 1.0
 */
@Getter
@Setter
public class ReissueJwtRequestDto {

    @NotBlank(message = "refreshToken 은 필수 값 입니다.")
    private String refreshToken;

    @NotNull(message = "userNo 값이 필요합니다.")
    private Integer memberNo;

    @Size(min = 1, max = 1, message = "하나의 권한만 가져야 합니다.")
    private List<String> authorities;
}
