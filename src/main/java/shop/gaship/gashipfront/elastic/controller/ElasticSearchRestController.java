package shop.gaship.gashipfront.elastic.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.elastic.dto.response.SearchResponseDto;
import shop.gaship.gashipfront.elastic.service.SearchService;

/**
 * 상품 검색을 ajax로 받기위한 컨트롤러입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class ElasticSearchRestController {
    private final SearchService searchService;

    @GetMapping
    public List<SearchResponseDto> searchProductKeyword(@RequestParam("keyword") String keyword) {
        return searchService.searchProductKeyword(keyword);
    }
}
