package shop.gaship.gashipfront.daylabor.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import shop.gaship.gashipfront.config.ServerConfig;
import shop.gaship.gashipfront.daylabor.dto.DayLaborDto;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * api 서버에 지역별일량을 요청하는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Component
@RequiredArgsConstructor
public class DayLaborAdapter {

    private final ServerConfig config;
    private static final String DAY_LABOR_URI = "/api/dayLabors";

    /**
     * 지역별 물량을 추가하기위한 메서드입니다.
     *
     * @param dto 지역별물량에대한 정보가 들어가있다.
     * @return 정상적으로 완료시 true 를 반환합니다.
     */
    public boolean addDayLabor(DayLaborDto dto){
        WebClient.create(config.getGatewayUrl()).post()
            .uri(DAY_LABOR_URI)
            .bodyValue(dto)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
        return true;
    }

    /**
     * 지역별 물량의 최대개수를 수정하기위한 메서드입니다.
     *
     * @param dto 관련지역 번호와 최대물량이 들어갑니다.
     * @return 정상적으로 완료시 true 를 반환합니다.
     */
    public boolean modifyDayLabor(DayLaborDto dto){
        WebClient.create(config.getGatewayUrl()).put()
            .uri(DAY_LABOR_URI)
            .bodyValue(dto)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
        return true;
    }

    /**
     * 지역별 물량에대한 정보들이 담긴다.
     *
     * @param pageable 페이지 정보가 들어옵니다.
     * @return 지역별 물량에대한 정보들이 반환됩니다.
     */
    public Flux<DayLaborDto> dayLaborList(Pageable pageable){
        return WebClient.create(config.getGatewayUrl()).get()
            .uri(uriBuilder -> uriBuilder.path(DAY_LABOR_URI)
                .queryParam("size", pageable.getPageSize())
                .queryParam("page", pageable.getPageNumber())
                .build())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToFlux(DayLaborDto.class);
    }
}
