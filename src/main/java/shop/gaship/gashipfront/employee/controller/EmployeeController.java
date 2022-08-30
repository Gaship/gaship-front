package shop.gaship.gashipfront.employee.controller;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.gaship.gashipfront.addresslocal.dto.response.AddressLocalResponseDto;
import shop.gaship.gashipfront.addresslocal.service.AddressLocalService;
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

    private final AddressLocalService addressLocalService;

    private final PasswordEncoder passwordEncoder;

    /**
     * 직원등록 페이지로 이동하기위한 메서드입니다.
     *
     * @param model the model
     * @return 이동할 html
     */
    @GetMapping("/add")
    public String moveAddForm(Model model) {
        List<AddressLocalResponseDto> addressList = addressLocalService.addressList();
        model.addAttribute("addressList", addressList);
        return "layout/admin/employee/employeeAddForm";
    }

    /**
     * 직원수정 페이지로 이동하기위한 메서드입니다.
     *
     * @param employeeNo 직원번호가 기입됩니다.
     * @param model      the model
     * @return 이동할 html
     */
    @GetMapping("/modify/{employeeNo}")
    public String moveModifyForm(@PathVariable("employeeNo") Integer employeeNo,
                                 Model model) {
        EmployeeResponseDto employee = employeeService.employeeDetail(employeeNo);
        List<AddressLocalResponseDto> addressList = addressLocalService.addressList();

        model.addAttribute("addressList", addressList);
        model.addAttribute("employee", employee);

        return "layout/admin/employee/employeeModifyForm";
    }

    /**
     * 직원이 등록될때 사용되는 post 메서드입니다.
     *
     * @param dto 이름,이메일(아이디),비밀번호,휴대폰 번호 등이 기입됩니다.
     * @return 생성후 직원의 메인페이지로 갑니다.
     */
    @PostMapping
    public String addEmployee(EmployeeCreateRequestDto dto) {
//        회원번호 26번 고정
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setAuthorityNo(26);
        employeeService.employeeAdd(dto);
        return "redirect:/admin/employees";
    }

    /**
     * 직원수정을 위한 PUT 메서드입니다.
     *
     * @param employeeNo 직원번호가 기입됩니다.
     * @param dto  이름,비밀번호,휴대폰 번호 등이 기입됩니다.
     * @return 수정후 직원의 메인페이지로 이동합니다.
     */
    @PutMapping("/{employeeNo}")
    public String modifyEmployee(
        @PathVariable("employeeNo") Integer employeeNo, EmployeeModifyRequestDto dto) {
        if(!dto.getPassword().isEmpty()){
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        dto.setEmployeeNo(employeeNo);
        employeeService.employeeModify(dto);
        return "redirect:/admin/employees";
    }

    /**
     * 직원의 메인페이지로 보내주는 GET 메서드입니다.
     * page 값을 입력받아 원하는 페이지로 보내집니다.
     *
     * @param page  보고싶은 페이지값이 기입됩니다.
     * @param model the model
     * @return 직원 메인 페이지입니다.
     */
    @GetMapping
    public String employeeList(@RequestParam(value = "page", required = false) Integer page,
                               Model model) {
        if (Objects.isNull(page)) {
            page = 0;
        }
        Pageable pageRequest = PageRequest.of(page, 10);
        PageResponse<EmployeeResponseDto> employees = employeeService.employeeList(pageRequest);

        model.addAttribute("uri", "/admin/employees");
        model.addAttribute("employees", employees.getContent());
        model.addAttribute("next", employees.isNext());
        model.addAttribute("previous", employees.isPrevious());
        model.addAttribute("totalPage", employees.getTotalPages());
        model.addAttribute("pageNum", employees.getNumber() + 1);
        model.addAttribute("previousPageNo", pageRequest.getPageNumber() - 1);
        model.addAttribute("nextPageNo", pageRequest.getPageNumber() + 1);

        return "layout/admin/employee/employeeList";
    }
}
