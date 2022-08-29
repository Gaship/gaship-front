package shop.gaship.gashipfront.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.employee.service.EmployeeService;

/**
 * 비동기 방식을 처리하기위한 rest controller 입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeRestController {
    private final EmployeeService employeeService;

    @DeleteMapping("/{employeeNo}")
    public void removeEmployee(@PathVariable("employeeNo") Integer employeeNo){
        employeeService.employeeDelete(employeeNo);
    }

}
