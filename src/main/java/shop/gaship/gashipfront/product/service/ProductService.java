package shop.gaship.gashipfront.product.service;

import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 제품 관련 데이터처리를 담당하는 서비스레이어 입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public interface ProductService {
    /**
     * 품목정보 페이징 리스트를 조회합니다.
     *
     * @param page 요청 페이지 번호
     * @param size 요청 페이지 당 가져올 게시물 수
     * @return 품목정보 페이징 리스트 객체를 반환합니다.
     */
    PageResponse<ProductAllInfoResponseDto> productAllInfoByPageable(
        String page, String size, String category, String minAmount, String maxAmount);
}
