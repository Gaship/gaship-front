package shop.gaship.gashipfront.product.adapter;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipfront.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipfront.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipfront.product.dto.request.SalesStatusModifyRequestDto;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 상품 어댑터 인터페이스입니다.
 *
 * @author : 김보민
 * @see shop.gaship.gashipfront.product.adapter.ProductAdapter
 * @since 1.0
 */
public interface ProductAdapter {
    /**
     * 어댑터 상품 추가 메서드입니다.
     *
     * @param multipartFiles         상품 등록 시 이미지 파일
     * @param createRequest 상품 등록 요청
     */
    void productAdd(List<MultipartFile> multipartFiles, ProductCreateRequestDto createRequest);

    /**
     * 어댑터 상품 수정 메서드입니다.
     *
     * @param multipartFiles         상품 수정 시 이미지 파일
     * @param modifyRequest 상품 수정 요청
     */
    void productModify(List<MultipartFile> multipartFiles, ProductModifyRequestDto modifyRequest);

    /**
     * 어댑터 상품 판매상태 수정 메서드입니다.
     *
     * @param modifyRequest 판매상태 수정 요청
     */
    void salesStatusModify(SalesStatusModifyRequestDto modifyRequest);

    /**
     * 코드로 상품을 검색하는 메서드입니다.
     *
     * @param productCode 검색할 상품코드
     * @param pageable    요청 페이지 정보
     * @return 페이지 객체
     */
    PageResponse<ProductAllInfoResponseDto> productListSearchCode(String productCode,
                                                                  Pageable pageable);

    /**
     * 상품 상세조회 메서드입니다.
     *
     * @param productNo 상세조회할 상품 번호
     * @return 상품 응답 객체
     */
    ProductAllInfoResponseDto productDetails(Integer productNo);

    /**
     * 판매상태로 상품 목록을 조회하는 메서드입니다.
     *
     * @param statusName 상품 판매상태
     * @param pageable   요청 페이지 정보
     * @return 페이지 객체
     */
    PageResponse<ProductAllInfoResponseDto> productListSearchStatusCode(String statusName,
                                                                        Pageable pageable);

    /**
     * 금액의 범위에따라 상품 목록을 조회하는 메서드입니다.
     *
     * @param minAmount 최소 금액
     * @param maxAmount 최대 금액
     * @param pageable  요청 페이지 정보
     * @return 페이지 객체
     */
    PageResponse<ProductAllInfoResponseDto> productAmountList(Long minAmount, Long maxAmount,
                                                              Pageable pageable);

    /**
     * 카테고리에 따라 상품 목록을 조회하는 메서드입니다.
     *
     * @param categoryNo 카테고리 번호
     * @param pageable   요청 페이지 정보
     * @return 페이지 객체
     */
    PageResponse<ProductAllInfoResponseDto> productCategoryList(Integer categoryNo, Pageable pageable);

    /**
     * 상품 이름으로 상품 목록을 조회하는 메서드입니다.
     *
     * @param name     검색할 상품 이름
     * @param pageable 요청 페이지 정보
     * @return 페이지 객체
     */
    PageResponse<ProductAllInfoResponseDto> productNameList(String name, Pageable pageable);

    /**
     * 상품 전체조회 메서드입니다.
     *
     * @param page 요청 페이지 번호
     * @param size 요청 페이지 당 가져올 게시물 수
     * @return 페이지 객체
     */
    PageResponse<ProductAllInfoResponseDto> productListAll(String page, String size,
                                                           String category, String minAmount,
                                                           String maxAmount);

    List<ProductAllInfoResponseDto> productNosList(List<Integer> productNos);
}
