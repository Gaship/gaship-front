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
    /**
     * 상태코드 다건 조회 요청을 위한 메서드 입니다.
     *
     * @param groupCodeName the group code name
     * @return 상태코드 목록을 반환합니다.
     */
    List<StatusCodeResponseDto> getStatusCodeList(String groupCodeName);
}
