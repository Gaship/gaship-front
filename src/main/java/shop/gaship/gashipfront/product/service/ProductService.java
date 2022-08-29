package shop.gaship.gashipfront.product.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipfront.product.dto.request.ProductCreateRequestDto;
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
     * @param category 카테고리 번호
     * @param minAmount 검색 최저 금액
     * @param maxAmount 검색 최고 금액
     * @return 품목정보 페이징 리스트 객체를 반환합니다.
     */
    PageResponse<ProductAllInfoResponseDto> productAllInfoByPageable(
        String page, String size, String category, String minAmount, String maxAmount);


    /**
     * 카테고리 번호를 통해서 품목정보 페이징 리스트를 조회합니다.
     *
     * @param page 요청 페이지 번호
     * @param category 카테고리 번호
     * @return 품목정보 페이징 리스트 객체를 반환합니다.
     */
    PageResponse<ProductAllInfoResponseDto> productCategoryByPageable(
        Pageable page, String category);

    List<ProductAllInfoResponseDto> findProductNosList(List<Integer> productNos);

    ProductAllInfoResponseDto findProduct(Integer productNo);

    void addProduct(List<MultipartFile> multipartFiles, ProductCreateRequestDto createRequest);
}
