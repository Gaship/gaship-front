package shop.gaship.gashipfront.employee.controller;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.gaship.gashipfront.employee.dto.request.EmployeeCreateRequestDto;
import shop.gaship.gashipfront.employee.dto.request.EmployeeModifyRequestDto;
import shop.gaship.gashipfront.employee.dto.response.EmployeeResponseDto;
import shop.gaship.gashipfront.employee.service.EmployeeService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */

@Controller
@RequestMapping("/admin/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public String addEmployee(@RequestBody EmployeeCreateRequestDto dto) {
        employeeService.employeeAdd(dto);
        return "redirect:layout/admin/employee/employeeAddForm";
    }

    @PutMapping("/{employeeNo}")
    public String modifyEmployee(@RequestBody EmployeeModifyRequestDto dto) {
        employeeService.employeeModify(dto);
        return "redirect:layout/admin/employee/employeeList";
    }

    @GetMapping("/{employeeNo}")
    public String employeeDetail(@PathVariable("employeeNo") Integer employeeNo) {
        employeeService.employeeDetail(employeeNo);
        return "?"; //필요할 곳이 있을까?
    }

    @GetMapping
    public String employeeList(@RequestParam(value = "page", required = false) Integer page,
                               Model model) {
        if (Objects.isNull(page)) {
            page = 0;
        }
        PageResponse<EmployeeResponseDto> employees = employeeService.employeeList(PageRequest.of(page, 10));
//
        model.addAttribute("employees", employees.getContent());
        model.addAttribute("next", employees.isNext());
        model.addAttribute("previous", employees.isPrevious());
        model.addAttribute("totalPage", employees.getTotalPages());
        model.addAttribute("pageNum", employees.getNumber() + 1);

        return "layout/admin/employee/employeeList";
    }
}
