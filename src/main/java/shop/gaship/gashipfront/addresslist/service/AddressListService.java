package shop.gaship.gashipfront.addresslist.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.addresslist.dto.request.AddressAddRequestDto;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListAddRequestDto;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListModifyRequestDto;
import shop.gaship.gashipfront.addresslist.dto.response.AddressListResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 배송지목록의 서비스 인터페이스.
 *
 * @author 최정우
 * @since 1.0
 */
public interface AddressListService {
    /**
     * 배송지를 생성하는 메서드.
     *
     * @param request 배송지 등록에 필요한 정보가 담겨 있는 dto.
     */
    void addAddressList(AddressListAddRequestDto request);

    /**
     * 배송지를 수정하는 메서드.
     *
     * @param request 배송지 정보 수정에 필요한 정보가 담겨 있는 dto.
     */
    void modifyAddressList(AddressListModifyRequestDto request);

    /**
     * 배송지를 삭제하는 메서드.
     *
     * @param memberNo 회원 id 값
     * @param addressListNo 배송지 id 값
     */
    void deleteAddressList(Long memberNo, Long addressListNo);

    /**
     * 배송지를 단건 조회하는 메서드.
     *
     * @param memberNo 회원 id 값
     * @param addressListNo 배송지 id 값
     * @return 배송지 조회 결과가 담긴 dto
     */
    AddressListResponseDto findAddressList(Long memberNo, Long addressListNo);

    /**
     * 상태가 "사용"인 배송지를 다건 조회하는 메서드.
     *
     * @param memberNo 회원 id 값
     * @param pageable 조회하려느 배송지 목록의 페이지 번호와 사이즈가 담겨있는 객체
     * @return 배송지 조회 결과가 담긴 dto Page
     */
    PageResponse<AddressListResponseDto> findAddressLists(Long memberNo, Pageable pageable);

    void addAddress(Integer memberNo, AddressAddRequestDto addressAddRequestDto);
}
