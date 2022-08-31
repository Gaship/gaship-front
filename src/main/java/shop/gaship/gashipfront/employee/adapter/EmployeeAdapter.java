package shop.gaship.gashipfront.employee.adapter;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.employee.dto.request.EmployeeCreateRequestDto;
import shop.gaship.gashipfront.employee.dto.request.EmployeeModifyRequestDto;
import shop.gaship.gashipfront.employee.dto.response.EmployeeResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 직접 통신해서 데이터를 조회하는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface EmployeeAdapter {


    /**
     * 직원을 추가하기위한 메서드입니다.
     *
     * @param requestDto 직원생성에 필요한 정보가 기입됩니다.
     */
    void employeeAdd(EmployeeCreateRequestDto requestDto);

    /**
     * 직원을 수정하기 위한 메서드입니다.
     *
     * @param requestDto 직원 수정에 필요한 정보가 기입됩니다.
     */
    void employeeModify(EmployeeModifyRequestDto requestDto);

    /**
     * 직원을 삭제할때 사용되는 메서드입니다.
     *
     * @param employeeNo 삭제할 직원의 번호가 기입됩니다.
     */
    void employeeRemove(Integer employeeNo);

    /**
     * 직원의 정보를 단건 조회할때 사용되는 메서드입니다..
     *
     * @param employeeNo 직원의 번호가 기입됩니다.
     * @return 직원의 정보가 반환됩니다.
     */
    EmployeeResponseDto employeeDetail(Integer employeeNo);

    /**
     * 전체 직원들으 정보를 조회할때 사용됩니다.
     *
     * @param pageable 관련 페이지 정보가 기입됩니다.
     * @return 직원의 정보가 페이징 리스트에 반환됩니다.
     */
    PageResponse<EmployeeResponseDto> employeeList(Pageable pageable);
}
