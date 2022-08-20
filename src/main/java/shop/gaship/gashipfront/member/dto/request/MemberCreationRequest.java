package shop.gaship.gashipfront.member.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 회원가입을 하기위한 가입 정보 dto입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Getter
@Builder
public class MemberCreationRequest {
    @Min(0)
    private Integer recommendMemberNo;

    @NotBlank
    @Length(max = 255)
    private String email;

    @NotBlank
    @Length(max = 100)
    @Pattern(
        // 첫글자는 대문자, 소문자, 숫자, @$!%*#?& 특수문자만 허용하여 8자리 이상 조합
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
        message = "영문, 숫자, 특수문자를 포함하여 8자리 이상이어야합니다."
    )
    private String password;

    @NotBlank
    @Length(max = 100)
    private String phoneNumber;

    @NotBlank
    @Length(max = 100)
    @Pattern(
        regexp = "^[가-힣]+",
        message = "이름을 정확히 입력하여주십시오."
    )
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank
    @Length(max = 20)
    @Pattern(
        regexp = "^[가-힣a-zA-Z\\d]{0,20}",
        message = "닉네임은 한글 또는 영문, 숫자만 입력이 가능합니다."
    )
    private String nickName;

    @NotBlank
    @Length(max = 1)
    private String gender;

    @NotNull
    private Boolean isVerifiedEmail;

    @NotNull
    private Boolean isUniqueEmail;

    public void changeHashedPassword(String hashedPassword) {
        this.password = hashedPassword;
    }
}
