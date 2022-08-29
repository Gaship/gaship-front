package shop.gaship.gashipfront.employee.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.employee.dto.request.EmployeeCreateRequestDto;
import shop.gaship.gashipfront.employee.dto.request.EmployeeModifyRequestDto;
import shop.gaship.gashipfront.employee.dto.response.EmployeeResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */
public interface EmployeeService {
    void employeeAdd(EmployeeCreateRequestDto requestDto);

    void employeeModify(EmployeeModifyRequestDto requestDto);

    EmployeeResponseDto employeeDetail(Integer employeeNo);

    PageResponse<EmployeeResponseDto> employeeList(Pageable pageable);
}
