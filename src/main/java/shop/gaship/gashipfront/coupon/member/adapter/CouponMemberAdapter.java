package shop.gaship.gashipfront.coupon.member.adapter;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.coupon.member.dto.CouponGenerationIssueDto;
import shop.gaship.gashipfront.coupon.member.dto.response.UnusedMemberCouponResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

import java.util.List;

/**
 * @author : 조재철
 * @author : 김세미
 * @since 1.0
 */
public interface CouponMemberAdapter {

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssue(Pageable pageable, Integer memberNo);

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUsed(Pageable pageable, Integer memberNo);

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnused(Pageable pageable,
        Integer memberNo);

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnusedExpired(Pageable pageable,
        Integer memberNo);

    PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnusedUnexpired(Pageable pageable,
        Integer memberNo);

    /**
     * 회원의 현재 사용가능한 쿠폰 다건 조회 요청을 위한 메서드입니다.
     *
     * @param memberNo 쿠폰 조회 대상이 되는 회원의 식별번호입니다.
     * @return 회원의 현재 사용가능한 쿠폰 목록을 반환합니다.
     * @author 김세미
     */
    List<UnusedMemberCouponResponseDto> findUnusedMemberCouponResponseDto(Integer memberNo);
}
