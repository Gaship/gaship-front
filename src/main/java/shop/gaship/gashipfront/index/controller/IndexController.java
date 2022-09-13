package shop.gaship.gashipfront.index.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
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
    private static final String FIRST_SERVER_HOST_NAME = "gaship-2";
    private static final String SECOND_SERVER_HOST_NAME = "gaship-3";

    @GetMapping("/")
    public String getIndex(Model model) throws UnknownHostException {
        model.addAttribute("host", getHostName());
        return "index";
    }

    private String getHostName() throws UnknownHostException {
        String host = InetAddress.getLocalHost().getHostName();

        if (Objects.equals(host, FIRST_SERVER_HOST_NAME)) {
            host = "1번 서버";
        } else if (Objects.equals(host, SECOND_SERVER_HOST_NAME)) {
            host = "2번 서버";
        } else {
            host = "";
        }

        return host;
    }
}
