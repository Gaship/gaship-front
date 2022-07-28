package shop.gaship.gashipfront.employee.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.config.ServerConfig;
import shop.gaship.gashipfront.employee.dto.request.EmployeeCreateRequestDto;
import shop.gaship.gashipfront.employee.dto.request.EmployeeModifyRequestDto;
import shop.gaship.gashipfront.employee.dto.response.EmployeeResponseDto;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * 쇼핑몰의 직원들에대한 데이터를 요청하기위한 Adaptor 입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Component
@RequiredArgsConstructor
public class EmployeeAdapter {
    private final ServerConfig baseurl;
    private static final String EMPLOYEE_BASE_URI = "/api/employees";

    /**
     * @param requestDto 직원생성시 들어갈 정보 입니다.
     * @return boolean 회원가입이 정상적으로 완료시 true 를 반환합니다.
     * @author 유호철
     */
    public boolean signUpRequest(EmployeeCreateRequestDto requestDto) {
        WebClient.create(baseurl.getGatewayUrl()).post()
            .uri(EMPLOYEE_BASE_URI)
            .bodyValue(requestDto)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);

        return true;
    }

    /**
     * @param requestDto 직원의 정보를 수정할때 들어갈 정보입니다.
     * @return boolean 직원정보가 제대로 수정되었을경우 true 를 반환합니다.
     * @author 유호철
     */
    public boolean modifyRequest(EmployeeModifyRequestDto requestDto){
        WebClient.create(baseurl.getGatewayUrl()).put()
            .uri(EMPLOYEE_BASE_URI)
            .bodyValue(requestDto)
            .retrieve()
            .onStatus(HttpStatus::isError,ExceptionUtil::createErrorMono);

        return true;
    }

    /**
     * @param employeeNo 조회할 직원의 번호 입니다.
     * @return mono 조회된 직원의 정보가 반환됩니다.
     * @author 유호철
     */

    public Mono<EmployeeResponseDto> getEmployee(Integer employeeNo){
        return WebClient.create(baseurl.getGatewayUrl()).get()
            .uri(EMPLOYEE_BASE_URI + employeeNo)
            .retrieve()
            .bodyToMono(EmployeeResponseDto.class);
    }

    /**
     * @return flux 조회된 직원들의 정보가 반환됩니다.
     * @author 유호철
     */
    public Flux<EmployeeResponseDto> getEmployees(PageRequest pageRequest){
        return WebClient.create(baseurl.getGatewayUrl()).get()
            .uri(uriBuilder -> uriBuilder.path(EMPLOYEE_BASE_URI)
                .queryParam("size",pageRequest.getPageSize())
                .queryParam("page",pageRequest.getPageNumber())
                .build())
            .retrieve()
            .bodyToFlux(EmployeeResponseDto.class);
    }
}

