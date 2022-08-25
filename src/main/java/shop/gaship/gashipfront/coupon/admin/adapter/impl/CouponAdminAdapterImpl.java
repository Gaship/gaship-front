package shop.gaship.gashipfront.coupon.admin.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.coupon.admin.adapter.CouponAdminAdapter;
import shop.gaship.gashipfront.coupon.dto.CouponTypeCreationDto;
import shop.gaship.gashipfront.coupon.dto.CouponTypeDto;
import shop.gaship.gashipfront.security.common.exception.NullResponseBodyException;
import shop.gaship.gashipfront.util.ExceptionUtil;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 조재철
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class CouponAdminAdapterImpl implements CouponAdminAdapter {

    private final WebClient webClient;
    public static final String COUPON_TYPE_PREFIX_URL = "/api/coupons/coupon-types";

    @Override
    public void addFixedRateCouponType(CouponTypeCreationDto couponTypeConvertDto) {
        webClient.post().
                 uri(COUPON_TYPE_PREFIX_URL + "/fixed-rate")
                 .bodyValue(couponTypeConvertDto)
                 .retrieve()
                 .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                 .toEntity(Void.class)
                 .block();
    }

    @Override
    public void addFixedAmountCouponType(CouponTypeCreationDto couponTypeConvertDto) {
        webClient.post()
                 .uri(COUPON_TYPE_PREFIX_URL + "/fixed-amount")
                 .bodyValue(couponTypeConvertDto)
                 .retrieve()
                 .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                 .toEntity(Void.class)
                 .block();
    }

    @Override
    public void modifyRecommendMemberCoupon(CouponTypeCreationDto couponTypeConvertDto) {
        webClient.put()
                 .uri(COUPON_TYPE_PREFIX_URL + "/recomment-member-coupon")
                 .bodyValue(couponTypeConvertDto)
                 .retrieve()
                 .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                 .toEntity(Void.class)
                 .block();
    }

    @Override
    public void modifyCouponTypeStopGenerationIssue(Integer couponTypeNo) {
        webClient.patch()
                 .uri(COUPON_TYPE_PREFIX_URL + "/" + couponTypeNo + "/recomment-member-coupon")
                 .retrieve()
                 .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                 .toEntity(Void.class)
                 .block();
    }

    @Override
    public void deleteCouponType(Integer couponTypeNo) {
        webClient.delete()
                 .uri(COUPON_TYPE_PREFIX_URL + "/" + couponTypeNo)
                 .retrieve()
                 .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                 .toEntity(Void.class)
                 .block();
    }

    @Override
    public PageResponse<CouponTypeDto> findCouponTypeList(Pageable pageable) {
        return webClient.get()
                        .uri(COUPON_TYPE_PREFIX_URL + "?page=" + pageable.getPageNumber() + "&size="
                            + pageable.getPageSize())
                        .retrieve()
                        .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                        .bodyToMono(new ParameterizedTypeReference<PageResponse<CouponTypeDto>>() {
                        })
                        .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public PageResponse<CouponTypeDto> findCouponTypeCanDeleteList(Pageable pageable) {
        return webClient.get()
                        .uri(COUPON_TYPE_PREFIX_URL + "/delete-can?page=" + pageable.getPageNumber() + "&size="
                            + pageable.getPageSize())
                        .retrieve()
                        .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                        .bodyToMono(new ParameterizedTypeReference<PageResponse<CouponTypeDto>>() {
                        })
                        .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public PageResponse<CouponTypeDto> findCouponTypeCannotDeleteList(Pageable pageable) {
        return webClient.get()
                        .uri(COUPON_TYPE_PREFIX_URL + "/delete-cannot?page=" + pageable.getPageNumber() + "&size="
                            + pageable.getPageSize())
                        .retrieve()
                        .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                        .bodyToMono(new ParameterizedTypeReference<PageResponse<CouponTypeDto>>() {
                        })
                        .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public PageResponse<CouponTypeDto> findCouponTypeFixedAmountList(Pageable pageable) {
        return webClient.get()
                        .uri(COUPON_TYPE_PREFIX_URL + "/fixed-amount?page=" + pageable.getPageNumber() + "&size="
                            + pageable.getPageSize())
                        .retrieve()
                        .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                        .bodyToMono(new ParameterizedTypeReference<PageResponse<CouponTypeDto>>() {
                        })
                        .blockOptional().orElseThrow(NullResponseBodyException::new);

    }

    @Override
    public PageResponse<CouponTypeDto> findCouponTypeFixedRateList(Pageable pageable) {
        return webClient.get()
                        .uri(COUPON_TYPE_PREFIX_URL + "/fixed-rate?page=" + pageable.getPageNumber() + "&size="
                            + pageable.getPageSize())
                        .retrieve()
                        .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                        .bodyToMono(new ParameterizedTypeReference<PageResponse<CouponTypeDto>>() {
                        })
                        .blockOptional().orElseThrow(NullResponseBodyException::new);

    }

}
