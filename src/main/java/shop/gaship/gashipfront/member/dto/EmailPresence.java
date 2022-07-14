package shop.gaship.gashipfront.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipfront.member.dto <br/>
 * fileName       : EmailPresence <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/14 <br/>
 * description    : 이메일이 존재하는지에 대한 데이터를 담기위한 dto입니다.<br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/14           김민수               최초 생성                         <br/>
 */
@Getter
@AllArgsConstructor
public class EmailPresence {
    private Boolean hasEmail;
}
