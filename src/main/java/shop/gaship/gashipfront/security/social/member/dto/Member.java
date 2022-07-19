package shop.gaship.gashipfront.security.social.member.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Serializable {
    private Integer memberNo;
    private String email;
    private List<String> authorities;
    private String password;
    private String nickName;
    private String name;
    private String gender;
    private String mobile;
    private LocalDate birthDate;
    // TODO : 테이블 컬럼 추가


    private Boolean social;

    public Boolean isSocial() {
        return social;
    }
}
