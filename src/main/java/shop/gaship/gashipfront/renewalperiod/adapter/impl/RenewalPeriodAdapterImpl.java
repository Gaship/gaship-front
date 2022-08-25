package shop.gaship.gashipfront.renewalperiod.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.renewalperiod.adapter.RenewalPeriodAdapter;
import shop.gaship.gashipfront.renewalperiod.dto.response.RenewalPeriodResponseDto;
import shop.gaship.gashipfront.renewalperiod.exception.RenewalPeriodNotFoundException;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 *
 *
 * @author : 김세미
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class RenewalPeriodAdapterImpl implements RenewalPeriodAdapter {
    public static final String RENEWAL_PERIOD_PATH = "/api/renewal-period";
    private final WebClient webClient;

    @Override
    public void modifyRenewalPeriod(Integer value) {
        webClient.put()
                .uri(RENEWAL_PERIOD_PATH + "?value=" + value)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    @Override
    public RenewalPeriodResponseDto findRenewalPeriod() {
        return webClient.get()
                .uri(RENEWAL_PERIOD_PATH)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(RenewalPeriodResponseDto.class)
                .blockOptional()
                .orElseThrow(RenewalPeriodNotFoundException::new);
    }
}
