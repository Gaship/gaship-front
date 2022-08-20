package shop.gaship.gashipfront.elastic.adapter.impl;

import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.elastic.adapter.SearchAdapter;
import shop.gaship.gashipfront.elastic.dto.response.SearchResponseDto;

/**
 * 엘라스틱 서치 검색을 활용하기위한 구현 클래스입니다.
 *
 * @author : 유호철
 * @see SearchAdapter
 * @since 1.0
 */
@Component
public class ElasticSearchAdapterImpl implements SearchAdapter {
    private WebClient webClient;


    /**
     * {@inheritDoc}
     */
    @Override
    public List<SearchResponseDto> searchName(String name){
       return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/search")
                .queryParam("productName",name)
                .build())
            .retrieve()
            .bodyToMono(
                new ParameterizedTypeReference<List<SearchResponseDto>>() {
                }
            ).block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SearchResponseDto> searchCode(String code) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/search")
                .queryParam("productCode",code)
                .build())
            .retrieve()
            .bodyToMono(
                new ParameterizedTypeReference<List<SearchResponseDto>>() {
                }
            ).block();
    }
}
