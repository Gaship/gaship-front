package shop.gaship.gashipfront.aspect.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : 조재철
 * @since 1.0
 */
@Getter
@Setter
public class ReissueJwtRequestDto {

    private String refreshToken;

    @NotBlank(message = "userNo 값이 필요합니다.")
    private Integer memberNo;

    private List<String> authorities;
}
