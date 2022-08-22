package shop.gaship.gashipfront.membergrade.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.membergrade.adapter.MemberGradeAdapter;
import shop.gaship.gashipfront.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipfront.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipfront.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipfront.membergrade.exception.MemberGradeNotFoundException;
import shop.gaship.gashipfront.util.ExceptionUtil;
import shop.gaship.gashipfront.util.dto.PageResponse;


/**
 * 회원등급에 대한 요청을 보내는 adapter 구현체입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberGradeAdapterImpl implements MemberGradeAdapter {
    public static final String MEMBER_GRADE_PATH = "/api/member-grades";
    private final WebClient webClient;

    @Override
    public void addMemberGrade(MemberGradeAddRequestDto requestDto) {
        webClient.post()
                .uri(MEMBER_GRADE_PATH)
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    @Override
    public void modifyMemberGrade(Integer memberGradeNo, MemberGradeModifyRequestDto requestDto) {
        webClient.put()
                .uri(MEMBER_GRADE_PATH + "/" + memberGradeNo)
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    @Override
    public void deleteAddressList(Integer memberGradeNo) {
        webClient.delete()
                .uri(MEMBER_GRADE_PATH + "/" + memberGradeNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    @Override
    public MemberGradeResponseDto findMemberGrade(Integer memberGradeNo) {
        return webClient.get()
                .uri(MEMBER_GRADE_PATH + "/" + memberGradeNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(MemberGradeResponseDto.class)
                .blockOptional()
                .orElseThrow(MemberGradeNotFoundException::new);
    }

    @Override
    public PageResponse<MemberGradeResponseDto> findMemberGradeList(Pageable pageable) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(MEMBER_GRADE_PATH)
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(new ParameterizedTypeReference<PageResponse<MemberGradeResponseDto>>() {
                })
                .block();
    }
}
