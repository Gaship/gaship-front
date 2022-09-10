package shop.gaship.gashipfront.addresslist.adapter.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.addresslist.adapter.AddressListAdapter;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListAddRequestDto;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListModifyRequestDto;
import shop.gaship.gashipfront.addresslist.dto.response.AddressListResponseDto;
import shop.gaship.gashipfront.security.common.exception.NullResponseBodyException;
import shop.gaship.gashipfront.util.ExceptionUtil;
import shop.gaship.gashipfront.util.TokenInjectUtil;
import shop.gaship.gashipfront.util.dto.PageResponse;

import java.util.List;

/**
 * 배송지목록 어뎁터 구현체입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class AddressListAdapterImpl implements AddressListAdapter {
    private static final String ADDRESS_LIST_PATH = "/api/members/{memberNo}/addressLists";
    private final WebClient webClient;
    private final TokenInjectUtil tokenInjectUtil;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAddressList(AddressListAddRequestDto request) {
        webClient.post()
                .uri("/api/members/{memberNo}/addressLists", request.getMemberNo())
                .contentType(MediaType.APPLICATION_JSON)
                .headers(tokenInjectUtil.headersConsumer())
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyAddressList(AddressListModifyRequestDto request) {
        Integer memberNo = request.getMemberNo();
        Integer addressListNo = request.getAddressListNo();
        webClient.put()
                .uri("/api/members/{memberNo}/addressLists/{addressListNo}",
                        memberNo, addressListNo)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(tokenInjectUtil.headersConsumer())
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAddressList(Integer memberNo, Long addressListNo) {
        webClient.delete()
                .uri("/api/members/{memberNo}/addressLists/{addressListNo}",
                        memberNo, addressListNo)
                .headers(tokenInjectUtil.headersConsumer())
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddressListResponseDto findAddressList(Integer memberNo, Long addressListNo) {
        return webClient.get()
                .uri("/api/members/{memberNo}/addressLists/{addressListNo}",
                        memberNo, addressListNo)
                .headers(tokenInjectUtil.headersConsumer())
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(AddressListResponseDto.class)
                .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<AddressListResponseDto> findAddressLists(Integer memberNo, Pageable pageable) {
        return webClient.get()
                .uri("/api/members/{memberNo}/addressLists?page=" + pageable.getPageNumber()
                        + "&size=" + pageable.getPageSize(), memberNo)
                .headers(tokenInjectUtil.headersConsumer())
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(new ParameterizedTypeReference<PageResponse<AddressListResponseDto>>() {
                })
                .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public List<AddressListResponseDto> findAddressListAll(Integer memberNo) {
        return webClient.get()
                .uri(ADDRESS_LIST_PATH, memberNo)
                .headers(tokenInjectUtil.headersConsumer())
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(new ParameterizedTypeReference<List<AddressListResponseDto>>() {
                })
                .blockOptional().orElseThrow(NullResponseBodyException::new);
    }
}
