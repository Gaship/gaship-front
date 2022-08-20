package shop.gaship.gashipfront.elastic.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.elastic.adapter.SearchAdapter;
import shop.gaship.gashipfront.elastic.dto.response.SearchResponseDto;
import shop.gaship.gashipfront.elastic.service.SearchService;

/**
 * 서비스레이어에서 검색을 사용하기위한 구 클래스입니다.
 *
 * @author : 유호철
 * @see SearchService
 * @since 1.0
 */

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final SearchAdapter searchAdapter;

    @Override
    public List<SearchResponseDto> searchProductKeyword(String productKeyword) {
        return searchAdapter.searchName(productKeyword);
    }
}
