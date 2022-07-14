package shop.gaship.gashipfront.verify.dto;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipfront.verify.dto <br/>
 * fileName       : RequestSuccessDto <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/14 <br/>
 * description    : 요청이 완료되었을 시 서버로부터 응답받는 dto입니다.<br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/14           김민수               최초 생성                         <br/>
 */
@Getter
public class RequestSuccessDto {
    private String requestStatus;
}
