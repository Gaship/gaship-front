package shop.gaship.gashipfront.employee.adaptor;

import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.employee.dto.request.EmployeeCreateRequestDto;
import shop.gaship.gashipfront.employee.dto.request.EmployeeModifyRequestDto;
import shop.gaship.gashipfront.employee.dto.response.EmployeeResponseDto;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */
public interface EmployeeAdaptor {
    /**
     * @param requestDto 직원생성시 들어갈 정보 입니다.
     * @return boolean 회원가입이 정상적으로 완료시 true 를 반환합니다.
     * @author 유호철
     */
    boolean signUpRequest(EmployeeCreateRequestDto requestDto);
    /**
     * @param requestDto 직원의 정보를 수정할때 들어갈 정보입니다.
     * @return boolean 직원정보가 제대로 수정되었을경우 true 를 반환합니다.
     * @author 유호철
     */
    boolean modifyRequest(EmployeeModifyRequestDto requestDto);
    /**
     * @param employeeNo 조회할 직원의 번호 입니다.
     * @return mono 조회된 직원의 정보가 반환됩니다.
     * @author 유호철
     */

    Mono<EmployeeResponseDto> getEmployee(Integer employeeNo);
    /**
     * @return flux 조회된 직원들의 정보가 반환됩니다.
     * @author 유호철
     */
    Flux<EmployeeResponseDto> getEmployees(PageRequest pageRequest);
}
