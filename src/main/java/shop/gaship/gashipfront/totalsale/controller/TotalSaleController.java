package shop.gaship.gashipfront.totalsale.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : 조재철
 * @since 1.0
 */
@RequestMapping("/admin/total-sale")
@Controller
public class TotalSaleController {

    @GetMapping
    public String totalSale() {

        return "totalsale/admin/totalSale";
    }
}
