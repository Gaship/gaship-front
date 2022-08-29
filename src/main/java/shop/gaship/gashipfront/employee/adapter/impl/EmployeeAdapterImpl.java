package shop.gaship.gashipfront.employee.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.employee.adapter.EmployeeAdapter;
import shop.gaship.gashipfront.employee.dto.request.EmployeeCreateRequestDto;
import shop.gaship.gashipfront.employee.dto.request.EmployeeModifyRequestDto;
import shop.gaship.gashipfront.employee.dto.response.EmployeeResponseDto;
import shop.gaship.gashipfront.util.ExceptionUtil;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 쇼핑몰의 직원들에대한 데이터를 요청하기위한 Adaptor 입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Component
@RequiredArgsConstructor
public class EmployeeAdapterImpl implements EmployeeAdapter {
    private static final String EMPLOYEE_BASE_URI = "/api/employees";

    private final WebClient webClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public void employeeAdd(EmployeeCreateRequestDto requestDto) {
        webClient
            .post()
            .uri(EMPLOYEE_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestDto)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(Void.class)
            .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void employeeModify(@RequestBody EmployeeModifyRequestDto requestDto) {
        webClient
            .put()
            .uri(EMPLOYEE_BASE_URI +"/{employeeNo}",requestDto.getEmployeeNo())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestDto)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(Void.class)
            .block();
    }

    @Override
    public EmployeeResponseDto employeeDetail(Integer employeeNo) {
        return webClient
            .get()
            .uri(EMPLOYEE_BASE_URI + employeeNo)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(EmployeeResponseDto.class)
            .block();
    }

    @Override
    public PageResponse<EmployeeResponseDto> employeeList(Pageable pageable) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path(EMPLOYEE_BASE_URI)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(
                new ParameterizedTypeReference<PageResponse<EmployeeResponseDto>>() {
                }
            ).block();
    }

}

