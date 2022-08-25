package shop.gaship.gashipfront.addresslocal.adpter;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.addresslocal.dto.request.AddressLocalModifyRequestDto;
import shop.gaship.gashipfront.addresslocal.dto.response.AddressLocalResponseDto;
import shop.gaship.gashipfront.addresslocal.dto.response.AddressSubLocalResponseDto;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * api 로 지역정보를 요청하는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Component
public class AddressLocalAdapter {

    private static final String ADDRESS_LOCALS = "/api/addressLocals";

    @Value("${gaship-server.gateway-url}")
    private String gateWayUrl;

    /**
     * 지역의 배달가능여부를 수정하기위한 메서드입니다.
     *
     * @param dto 지역의 배달여부를 수정하는 정보가들어 있습니다.
     * @return 정상적으로 완료시 true 를 반환합니다.
     */
    public boolean modifyAddressIsDelivery(AddressLocalModifyRequestDto dto) {
        WebClient.create(gateWayUrl)
            .put()
            .uri(ADDRESS_LOCALS)
            .bodyValue(dto)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
        return true;
    }

    /**
     * 지역들의 정보들이 반환되는 메서드입니다.
     *
     * @param upperAddress  최상위 지역이 검색됩니다.
     * @return 지역 정보들이 반환됩니다.
     */
    public List<AddressSubLocalResponseDto> addressSubLocalList(String upperAddress) {
        return WebClient.create(gateWayUrl)
            .get()
            .uri(uriBuilder -> uriBuilder.path(ADDRESS_LOCALS)
                .queryParam("address", upperAddress)
                .build())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(
                new ParameterizedTypeReference<List<AddressSubLocalResponseDto>>() {
                }
            ).block();
    }

    public List<AddressLocalResponseDto> addressLocalList(){
        return WebClient.create(gateWayUrl)
            .get()
            .uri(ADDRESS_LOCALS)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(
                new ParameterizedTypeReference<List<AddressLocalResponseDto>>() {
                }
            ).block();
    }
}

