package shop.gaship.gashipfront.coupon.member.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.coupon.member.adapter.CouponMemberAdapter;
import shop.gaship.gashipfront.coupon.member.dto.CouponGenerationIssueDto;
import shop.gaship.gashipfront.coupon.member.dto.response.UnusedMemberCouponResponseDto;
import shop.gaship.gashipfront.security.common.exception.NullResponseBodyException;
import shop.gaship.gashipfront.util.ExceptionUtil;
import shop.gaship.gashipfront.util.dto.PageResponse;

import java.util.List;

/**
 * @author : 조재철
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class CouponMemberAdapterImpl implements CouponMemberAdapter {

    private final WebClient webClient;
    public static final String COUPON_TYPE_PREFIX_URL = "/api/coupons/coupon-generations-issues";

    @Override
    public PageResponse<CouponGenerationIssueDto> findCouponGenerationIssue(Pageable pageable,
        Integer memberNo) {
        return webClient.get()
                        .uri(COUPON_TYPE_PREFIX_URL + "/member/" + memberNo + "?page=" + pageable.getPageNumber() + "&size="
                            + pageable.getPageSize())
                        .retrieve()
                        .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                        .bodyToMono(new ParameterizedTypeReference<PageResponse<CouponGenerationIssueDto>>() {
                        })
                        .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUsed(Pageable pageable,
        Integer memberNo) {
        return webClient.get()
                        .uri(COUPON_TYPE_PREFIX_URL + "/member/" + memberNo + "/used-coupons?page=" + pageable.getPageNumber() + "&size="
                            + pageable.getPageSize())
                        .retrieve()
                        .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                        .bodyToMono(new ParameterizedTypeReference<PageResponse<CouponGenerationIssueDto>>() {
                        })
                        .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnused(Pageable pageable,
        Integer memberNo) {
        return webClient.get()
                        .uri(COUPON_TYPE_PREFIX_URL + "/member/" + memberNo + "/unused-coupons?page=" + pageable.getPageNumber() + "&size="
                            + pageable.getPageSize())
                        .retrieve()
                        .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                        .bodyToMono(new ParameterizedTypeReference<PageResponse<CouponGenerationIssueDto>>() {
                        })
                        .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnusedExpired(Pageable pageable,
        Integer memberNo) {
        return webClient.get()
                        .uri(COUPON_TYPE_PREFIX_URL + "/member/" + memberNo + "/unused-coupons/expired-coupons?page=" + pageable.getPageNumber() + "&size="
                            + pageable.getPageSize())
                        .retrieve()
                        .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                        .bodyToMono(new ParameterizedTypeReference<PageResponse<CouponGenerationIssueDto>>() {
                        })
                        .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public PageResponse<CouponGenerationIssueDto> findCouponGenerationIssueUnusedUnexpired(Pageable pageable,
        Integer memberNo) {
        return webClient.get()
                        .uri(COUPON_TYPE_PREFIX_URL + "/member/" + memberNo + "/unused-coupons/unexpired-coupons?page=" + pageable.getPageNumber() + "&size="
                            + pageable.getPageSize())
                        .retrieve()
                        .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                        .bodyToMono(new ParameterizedTypeReference<PageResponse<CouponGenerationIssueDto>>() {
                        })
                        .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public List<UnusedMemberCouponResponseDto> findUnusedMemberCouponResponseDto(Integer memberNo) {
        return webClient.get()
                .uri(COUPON_TYPE_PREFIX_URL + "?memberNo=" +memberNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(new ParameterizedTypeReference<List<UnusedMemberCouponResponseDto>>() {
                })
                .blockOptional().orElseThrow(NullResponseBodyException::new);
    }
}
