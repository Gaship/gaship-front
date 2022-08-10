package shop.gaship.gashipfront.addresslist.adapter.Impl;

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
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 배송지목록 어뎁터 구현체입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Component
public class AddressListAdapterImpl implements AddressListAdapter {
    private static final String BASE_URL = "http://localhost:7070";
    private static final String ADDRESS_LIST_BASE_URL = "/api/members/{memberNo}/addressLists";
    private static final String ADDRESS_LIST_SPECIFIC_URL = "/{addressListNo}";


    private final WebClient webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .build();

    @Override
    public void addAddressList(AddressListAddRequestDto request) {
        webClient.post()
                .uri(ADDRESS_LIST_BASE_URL, request.getMemberNo())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    @Override
    public void modifyAddressList(AddressListModifyRequestDto request) {
        webClient.put()
                .uri(ADDRESS_LIST_BASE_URL + ADDRESS_LIST_SPECIFIC_URL,
                        request.getMemberNo(), request.getAddressListNo())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    @Override
    public void deleteAddressList(Long memberNo, Long addressListNo) {
        webClient.delete()
                .uri(ADDRESS_LIST_BASE_URL + ADDRESS_LIST_SPECIFIC_URL,
                        memberNo, addressListNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    @Override
    public AddressListResponseDto findAddressList(Long memberNo, Long addressListNo) {
        return webClient.get()
                .uri(ADDRESS_LIST_BASE_URL + ADDRESS_LIST_SPECIFIC_URL,
                        memberNo, addressListNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(AddressListResponseDto.class)
                .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public PageResponse<AddressListResponseDto> findAddressLists(Long memberNo, Pageable pageable) {
        return webClient.get()
                .uri(ADDRESS_LIST_BASE_URL, memberNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(new ParameterizedTypeReference<PageResponse<AddressListResponseDto>>() {
                })
                .blockOptional().orElseThrow(NullResponseBodyException::new);
    }
}
