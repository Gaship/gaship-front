package shop.gaship.gashipfront.addresslist.dto.request;

import lombok.Getter;

/**
 * 배송지 등록 요청 Dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public class AddressAddRequestDto {
    private String sigungu;
    private String roadAddress;
    private String addressDetail;
    private String zonecode;
}
