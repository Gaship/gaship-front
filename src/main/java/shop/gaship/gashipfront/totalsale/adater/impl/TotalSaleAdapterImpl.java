package shop.gaship.gashipfront.totalsale.adater.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.common.exception.NullResponseBodyException;
import shop.gaship.gashipfront.totalsale.adater.TotalSaleAdapter;
import shop.gaship.gashipfront.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipfront.totalsale.dto.response.TotalSaleResponseDto;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * @author : 조재철
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class TotalSaleAdapterImpl implements TotalSaleAdapter {

    private final WebClient webClient;
    public static final String TOTAL_SALE_PREFIX_URL = "/api/total-sale";

    @Override
    public List<TotalSaleResponseDto> findTotalSaleList(
        TotalSaleRequestDto totalSaleRequestDto) {
        return webClient.post()
                        .uri(TOTAL_SALE_PREFIX_URL)
                        .bodyValue(totalSaleRequestDto)
                        .retrieve()
                        .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                        .bodyToMono(new ParameterizedTypeReference<List<TotalSaleResponseDto>>() {
                        })
                        .blockOptional().orElseThrow(NullResponseBodyException::new);
    }
}
