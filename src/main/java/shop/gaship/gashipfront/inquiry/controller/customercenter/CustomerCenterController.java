package shop.gaship.gashipfront.inquiry.controller.customercenter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 최겸준
 * @since 1.0
 */
@Controller
@RequestMapping("/customer-center")
public class CustomerCenterController {

    @GetMapping
    public String showCustomerCenter() {
        return "inquiry/customerCenterHome";
    }
}
