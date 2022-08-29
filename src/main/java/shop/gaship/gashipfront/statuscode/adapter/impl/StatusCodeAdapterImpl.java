package shop.gaship.gashipfront.statuscode.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.statuscode.adapter.StatusCodeAdapter;
import shop.gaship.gashipfront.statuscode.dto.response.StatusCodeResponseDto;
import shop.gaship.gashipfront.util.ExceptionUtil;

import java.util.List;

/**
 * 상태코드 관련 요청을 위한 adapter 구현체입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class StatusCodeAdapterImpl implements StatusCodeAdapter {
    private static final String STATUS_CODE_PATH = "/api/status-codes";
    private final WebClient webClient;

    @Override
    public List<StatusCodeResponseDto> getStatusCodeList(String groupCodeName) {
        return webClient.get()
                .uri(STATUS_CODE_PATH + "/" + groupCodeName)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(new ParameterizedTypeReference<List<StatusCodeResponseDto>>() {
                })
                .block();
    }
}
