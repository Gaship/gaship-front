package shop.gaship.gashipfront.employee.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 직원정보 수정을 위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@Builder
public class EmployeeModifyRequestDto {
    @Min(1)
    @NotNull
    private Integer employeeNo;

    @Pattern(
            regexp = "^[가-힣]+",
            message = "이름을 정확히 입력하여주십시오."
    )
    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    @Length(
            min = 11 ,max = 11,
            message = "휴대전화 번호를 다시 입력해주세요"
    )
    @NotNull
    private String phoneNo;
}
