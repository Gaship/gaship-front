package shop.gaship.gashipfront.security.common.member.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * member에 대한 dto 역할을 수행하기위한 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberDto implements Serializable {
    private Integer memberNo;
    private String memberStatus;

    private String email;
    private List<String> authorities;
    private String password;
    private String nickName;
    private String name;
    private String gender;
    private String phoneNumber;
    private LocalDate birthDate;

    private Long accumulatePurchaseAmount;
    private LocalDate nextRenewalGradeDate;
    private LocalDateTime registerDatetime;
    private LocalDateTime modifyDatetime;
    private Boolean social;
    private String encodedEmailForSearch;

    @Builder
    public MemberDto(String email, String password, String nickName, String name,
                     String gender, String phoneNumber, LocalDate birthDate) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public Boolean isSocial() {
        return social;
    }
}
