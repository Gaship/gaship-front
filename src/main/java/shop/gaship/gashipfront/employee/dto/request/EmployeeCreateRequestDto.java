package shop.gaship.gashipfront.employee.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 직원생성을 위한 정보가 담긴 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@Builder
public class EmployeeCreateRequestDto {

    @Min(0)
    @NotNull
    private Integer authorityNo;

    @Min(0)
    @NotNull
    private Integer addressNo;

    @Pattern(
            regexp = "^[가-힣]+",
            message = "이름을 정확히 입력하여주십시오."
    )
    @NotNull
    @Length(min = 1,max = 20)
    private String name;

    @NotNull
    @Length(max = 255)
    @Email
    private String email;

    @Pattern(
            // 첫글자는 대문자, 소문자, 숫자, @$!%*#?& 특수문자만 허용하여 8자리 이상 조합
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "영문, 숫자, 특수문자를 포함하여 8자리 이상이어야합니다."
    )
    @NotNull
    private String password;

    @NotNull
    @Length(max = 100)
    private String phoneNo;

}
