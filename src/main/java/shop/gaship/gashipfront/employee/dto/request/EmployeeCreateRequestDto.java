package shop.gaship.gashipfront.employee.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import reactor.util.annotation.Nullable;

/**
 * 직원생성을 위한 Dto 객체입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class EmployeeCreateRequestDto {
    @Min(1)
    @Nullable
    @Setter
    private Integer authorityNo;
    @Min(1)
    @NotNull(message = "지역번호를 입력해주세요")

    private Integer addressNo;
    @Pattern(
        regexp = "^[가-힣]+",
        message = "이름을 정확히 입력하여주십시오."
    )
    @NotNull(message = "이름을 입력해주세요")
    @Length(min = 1, max = 20)
    private String name;

    @NotNull(message = "이메일을 입력해주세요")
    @Email
    private String email;

    @Setter
    @Pattern(
        // 첫글자는 대문자, 소문자, 숫자, @$!%*#?& 특수문자만 허용하여 8자리 이상 조합
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
        message = "영문, 숫자, 특수문자를 포함하여 8자리 이상이어야합니다."
    )
    @NotNull(message = "비밀번호를 입력해주세요")
    private String password;

    @NotNull(message = "휴대전화번호를 입력해주세요")
    private String phoneNo;
}
