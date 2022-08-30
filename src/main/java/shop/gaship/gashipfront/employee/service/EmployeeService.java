package shop.gaship.gashipfront.employee.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.employee.dto.request.EmployeeCreateRequestDto;
import shop.gaship.gashipfront.employee.dto.request.EmployeeModifyRequestDto;
import shop.gaship.gashipfront.employee.dto.response.EmployeeResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 서비스 레이어에서 직원에 대해 사용되는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface EmployeeService {
    /**
     * 직원이 추가될때 사용되는 메서드입니다.
     *
     * @param requestDto 직원생성에 필요한 정보들이 기입됩니다.
     */
    void employeeAdd(EmployeeCreateRequestDto requestDto);

    /**
     * 직원이 수정될때 사용되는 메서드입니다.
     *
     * @param requestDto 직원수정에 필요한 정보들이 기입됩니다.
     */
    void employeeModify(EmployeeModifyRequestDto requestDto);

    /**
     * 직원을 삭제할때 사용되는 메서드입니다.
     *
     * @param employeeNo 삭제할 직원번호가 기입됩니다.
     */
    void employeeDelete(Integer employeeNo);

    /**
     * 직원의 단건 정보를 볼때 사용되는 메서드입니다.
     *
     * @param employeeNo 직원 정보가기입됩니다.
     * @return 직원의 정보가 반환됩니다.
     */
    EmployeeResponseDto employeeDetail(Integer employeeNo);

    /**
     * 모든 직원들의 정보를 볼때 사용되는 메서드입니다.
     *
     * @param pageable 관련 페이징 객체가 들어갑니다.
     * @return 필요한 페이징정보만 반환됩니다.
     */
    PageResponse<EmployeeResponseDto> employeeList(Pageable pageable);
}
