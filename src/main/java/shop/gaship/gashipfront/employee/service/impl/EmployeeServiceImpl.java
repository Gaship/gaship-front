package shop.gaship.gashipfront.employee.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.employee.adapter.impl.EmployeeAdapterImpl;
import shop.gaship.gashipfront.employee.dto.request.EmployeeCreateRequestDto;
import shop.gaship.gashipfront.employee.dto.request.EmployeeModifyRequestDto;
import shop.gaship.gashipfront.employee.dto.response.EmployeeResponseDto;
import shop.gaship.gashipfront.employee.service.EmployeeService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @see EmployeeService
 * @since 1.0
 */

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeAdapterImpl employeeAdapter;

    @Override
    public void employeeAdd(EmployeeCreateRequestDto requestDto){
        employeeAdapter.employeeAdd(requestDto);
    }

    @Override
    public void employeeModify(EmployeeModifyRequestDto requestDto){
        employeeAdapter.employeeModify(requestDto);
    }

    @Override
    public EmployeeResponseDto employeeDetail(Integer employeeNo){
        return employeeAdapter.employeeDetail(employeeNo);
    }

    @Override
    public PageResponse<EmployeeResponseDto> employeeList(Pageable pageable){
        return employeeAdapter.employeeList(pageable);
    }
}
