package shop.gaship.gashipfront.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 주문 controller 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @GetMapping
    public String orderForm() {
        return "order/orderForm";
    }
}
