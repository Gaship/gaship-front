package shop.gaship.gashipfront.statuscode.dto.response;

import lombok.Getter;

/**
 * 상태코드 조회 응답 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public class StatusCodeResponseDto {
    private Integer statusCodeNo;
    private String statusCodeName;
    private Integer priority;
}
