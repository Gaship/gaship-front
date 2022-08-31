package shop.gaship.gashipfront.inquiry.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;

/**
 * User권한이라면 본인의 번호와 대조하여 특정 게시물들이 본인의 게시물이 맞는지 체크하는 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class RoleUserMySelfProcessor {

    public static final Boolean USER = Boolean.TRUE;
    public static final Boolean NOT_USER = Boolean.FALSE;

    public static Boolean setSelfList(UserDetailsDto userDetailsDto,
                                      List<InquiryListResponseDto> inquiryList) {

        if (Objects.isNull(userDetailsDto)) {
            return NOT_USER;
        }

        List<String> authorities = userDetailsDto.getAuthorities().stream().map(authority -> authority.getAuthority())
            .collect(Collectors.toList());

        if (!authorities.contains("ROLE_USER")) {
            return NOT_USER;
        }

        inquiryList.stream().forEach(inquiry -> {

            if (inquiry.getMemberNo().equals(userDetailsDto.getMemberNo())) {
                inquiry.setSelf(true);
            }
        });

        return USER;
    }

    public static Boolean setSelf(UserDetailsDto userDetailsDto,
                                  InquiryDetailsResponseDto inquiryDetailsResponseDto) {

        if (Objects.isNull(userDetailsDto)) {
            return NOT_USER;
        }

        List<String> authorities = userDetailsDto.getAuthorities().stream().map(authority -> authority.getAuthority())
            .collect(Collectors.toList());

        if (!authorities.contains("ROLE_USER")) {
            return NOT_USER;
        }

        if (inquiryDetailsResponseDto.getMemberNo().equals(userDetailsDto.getMemberNo())) {
            inquiryDetailsResponseDto.setSelf(true);
        }

        return USER;
    }
}
