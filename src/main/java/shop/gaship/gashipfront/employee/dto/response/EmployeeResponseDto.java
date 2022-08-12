package shop.gaship.gashipfront.employee.dto.response;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 직원의 정보가 반환되는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {
    @NotNull
    @Length(min = 1, max = 20)
    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String phoneNo;

    @NotBlank
    private String address;
}
