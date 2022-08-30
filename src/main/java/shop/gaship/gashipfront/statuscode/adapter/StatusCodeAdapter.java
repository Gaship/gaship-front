package shop.gaship.gashipfront.statuscode.adapter;

import shop.gaship.gashipfront.statuscode.dto.response.StatusCodeResponseDto;

import java.util.List;

/**
 * 상태코드 관련 요청을 위한 adapter interface 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface StatusCodeAdapter {
    List<StatusCodeResponseDto> getStatusCodeList(String groupCodeName);
}
