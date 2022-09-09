package shop.gaship.gashipfront.inquiry.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.inquiry.adapter.InquiryAdapter;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.security.common.exception.NullResponseBodyException;
import shop.gaship.gashipfront.util.ExceptionUtil;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * InquiryAdapter를 구현한 클래스입니다.
 *
 * @author 최겸준
 * @see shop.gaship.gashipfront.inquiry.adapter.InquiryAdapter
 * @since 1.0
 */

@Component
@RequiredArgsConstructor
public class InquiryAdapterImpl implements InquiryAdapter {
    private final WebClient webClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public void inquiryAdd(InquiryAddRequestDto inquiryDto) {
        webClient.post().uri("/api/inquiries").contentType(MediaType.APPLICATION_JSON)
            .bodyValue(inquiryDto).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono).toEntity(void.class)
            .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void inquiryAnswerAdd(
        InquiryAnswerRequestDto inquiryAnswerAddRequestDto) {
        webClient.post().uri("/api/inquiries/inquiry-answer")
            .contentType(MediaType.APPLICATION_JSON).bodyValue(inquiryAnswerAddRequestDto)
            .retrieve().onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(void.class).block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void inquiryAnswerModify(InquiryAnswerRequestDto inquiryAnswerModifyRequestDto) {
        webClient.put().uri("/api/inquiries/{inquiryNo}/inquiry-answer",
                inquiryAnswerModifyRequestDto.getInquiryNo()).contentType(MediaType.APPLICATION_JSON)
            .bodyValue(inquiryAnswerModifyRequestDto).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono).toEntity(void.class)
            .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void inquiryDelete(Integer inquiryNo, Integer memberNo) {
        webClient.delete().uri(uriBuilder -> uriBuilder.path("/api/inquiries/{inquiryNo}")
                .queryParam("memberNo", memberNo)
                .build(inquiryNo))
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono).toEntity(void.class)
            .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void inquiryDeleteManager(Integer inquiryNo) {
        webClient.delete().uri("/api/inquiries/{inquiryNo}/manager", inquiryNo)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono).toEntity(void.class)
            .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void inquiryAnswerDelete(Integer inquiryNo) {
        webClient.delete().uri("/api/inquiries/{inquiryNo}/inquiry-answer", inquiryNo).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono).toEntity(void.class)
            .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InquiryDetailsResponseDto inquiryDetails(Integer inquiryNo) {

        return webClient.get().uri("/api/inquiries/{inquiryNo}", inquiryNo)
            .accept(MediaType.APPLICATION_JSON).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(InquiryDetailsResponseDto.class).blockOptional()
            .orElseThrow(NullResponseBodyException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> customerInquiryList(Pageable pageable) {

        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/inquiries/customer-inquiries")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<InquiryListResponseDto>>() {
            }).blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public PageResponse<InquiryListResponseDto> customerInquiryListPrevPage(Pageable pageable,
                                                                            Integer inquiryNo) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/inquiries/customer-inquiries/prev-page")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .queryParam("inquiryNo", inquiryNo)
                .build()).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<InquiryListResponseDto>>() {
            }).blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public PageResponse<InquiryListResponseDto> customerInquiryListNextPage(Pageable pageable,
                                                                            Integer inquiryNo) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/inquiries/customer-inquiries/next-page")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .queryParam("inquiryNo", inquiryNo)
                .build()).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<InquiryListResponseDto>>() {
            }).blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> productInquiryList(Pageable pageable) {

        return webClient.get().uri(uriBuilder -> uriBuilder.path("/api/inquiries/product-inquiries")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build()).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<InquiryListResponseDto>>() {
            }).blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> customerInquiryStatusHoldList(Pageable pageable) {

        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/inquiries/customer-inquiries/status-hold")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build()).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<InquiryListResponseDto>>() {
            }).blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public PageResponse<InquiryListResponseDto> customerInquiryStatusCompleteList(
        Pageable pageable) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/inquiries/customer-inquiries/status-complete")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build()).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<InquiryListResponseDto>>() {
            }).blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> customerInquiryStatusCompleteListPrevPage(
        Pageable pageable, Integer inquiryNo) {

        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/inquiries/customer-inquiries/status-complete/prev-page")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .queryParam("inquiryNo", inquiryNo)
                .build()).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<InquiryListResponseDto>>() {
            }).blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public PageResponse<InquiryListResponseDto> customerInquiryStatusCompleteListNextPage(
        Pageable pageable, Integer inquiryNo) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/inquiries/customer-inquiries/status-complete/next-page")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .queryParam("inquiryNo", inquiryNo)
                .build()).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<InquiryListResponseDto>>() {
            }).blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> productInquiryStatusHoldList(Pageable pageable) {

        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/inquiries/product-inquiries/status-hold")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build()).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<InquiryListResponseDto>>() {
            }).blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> productInquiryStatusCompleteList(
        Pageable pageable) {

        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/inquiries/product-inquiries/status-complete")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build()).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<InquiryListResponseDto>>() {
            }).blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> customerInquiryMemberList(Pageable pageable,
                                                                          Integer memberNo) {

        return webClient.get().uri(
                uriBuilder -> uriBuilder.path("/api/inquiries/member/{memberNo}/customer-inquiries")
                    .queryParam("page", pageable.getPageNumber())
                    .queryParam("size", pageable.getPageSize())
                    .build(memberNo))
            .retrieve().onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<InquiryListResponseDto>>() {
            }).blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> productInquiryMemberList(Pageable pageable,
                                                                         Integer memberNo) {

        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/inquiries/member/{memberNo}/product-inquiries")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build(memberNo))
            .retrieve().onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<InquiryListResponseDto>>() {
            }).blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> productInquiryProductList(Pageable pageable,
                                                                          Integer productNo) {

        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/inquiries/product/{productNo}")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build(productNo)).retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(new ParameterizedTypeReference<PageResponse<InquiryListResponseDto>>() {
            }).blockOptional().orElseThrow(NullResponseBodyException::new);
    }
}
