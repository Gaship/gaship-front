package shop.gaship.gashipfront.addresslist.adapter;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListAddRequestDto;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListModifyRequestDto;
import shop.gaship.gashipfront.addresslist.dto.response.AddressListResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 배송지목록 어뎁터 인터페이스입니다.
 * 
 * @author 최정우
 * @since 1.0
 */
public interface AddressListAdapter {
    
    /**
     * 배송지목록를 등록하는 어뎁터입니다.
     *
     * @param request 등록하려는 배송지목록의 정보가 들어있습니다.
     * @author 최정우
     */
    void addAddressList(AddressListAddRequestDto request);

    /**
     * 배송지목록를 수정하는 어뎁터입니다.
     *
     * @param request 수정하려는 배송지목록의 정보가 들어있습니다.
     * @author 최정우
     */
    void modifyAddressList(AddressListModifyRequestDto request);

    /**
     * 배송지목록를 삭제(상태변경)하는 어뎁터입니다.
     *
     * @param addressListNo 삭제하려는 배송지목록의 id 입니다.
     * @author 최정우
     */
    void deleteAddressList(Long addressListNo);

    /**
     * 배송지목록을 상세조회하는 어뎁터입니다.
     *
     * @param addressListNo 조회하려는 배송지의 id 입니다.
     * @author 최정우
     */
    AddressListResponseDto findAddressList(Long addressListNo);

    /**
     * 배송지목록을 다건조회 어뎁터입니다.
     *
     * @param memberNo 배송지목록을 조회하려는 대상 회원의 id 입니다.
     * @param pageable 조회하려는 페이지의 번호와 사이즈가 있습니다.
     * @author 최정우
     */
    PageResponse<AddressListResponseDto> findAddressLists(String memberNo, Pageable pageable);
}
