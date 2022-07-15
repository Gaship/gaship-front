package shop.gaship.gashipfront.verify.dto;

import lombok.Getter;

/**
 * 요청에 성공할 시 응답 상태를 받기 위한 dto객체입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Getter
public class RequestSuccessDto {
    private String requestStatus;
}
