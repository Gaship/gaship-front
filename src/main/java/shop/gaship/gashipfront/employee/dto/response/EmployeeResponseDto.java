package shop.gaship.gashipfront.employee.dto.response;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 직원의 정보를 받기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class EmployeeResponseDto {

    @NotNull
    @Length(min = 1, max = 20)
    private final String name;

    @Email
    @NotNull
    private final String email;

    @NotNull
    private final String phoneNo;

    @NotBlank
    private final String address;
}
