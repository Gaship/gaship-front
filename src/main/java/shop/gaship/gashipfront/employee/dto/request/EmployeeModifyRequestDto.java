package shop.gaship.gashipfront.employee.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 직원정보 수정을 위한 Dto 객체입니다.
 * @author : 유호철
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeModifyRequestDto {
    @Min(1)
    @NotNull(message = "직원번호를 입력해주세요")
    private Integer employeeNo;
    @Pattern(
        regexp = "^[가-힣]+",
        message = "이름을 정확히 입력하여주십시오."
    )
    @Length(min = 2,max = 100)
    @NotNull(message = "이름을 입력해주세요")
    private String name;

    @Email
    @NotNull(message = "이메일을 입력해주세요")
    private String email;

    @NotNull(message = "휴대폰번호를 입력해주세요")
    private String phoneNo;
}
