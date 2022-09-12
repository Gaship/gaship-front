package shop.gaship.gashipfront.index.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * index 컨트롤러입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String getIndex(Model model) throws UnknownHostException {
        model.addAttribute("host", InetAddress.getLocalHost().getHostName());
        return "index";
    }
}
