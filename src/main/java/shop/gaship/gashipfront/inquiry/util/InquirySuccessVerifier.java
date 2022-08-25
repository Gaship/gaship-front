package shop.gaship.gashipfront.inquiry.util;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_IS_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_SUCCESS_MESSAGE;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import shop.gaship.gashipfront.inquiry.exception.SuccessMessageNotFoundException;

/**
 * @author : 최겸준
 * @since 1.0
 */
public class InquirySuccessVerifier {
    public static void verify(Model model, HttpSession session) {
        Boolean isSuccess = (Boolean) session.getAttribute(KEY_IS_SUCCESS.getValue());
        if (!isSuccess) {
            return;
        }

        String message = Optional.ofNullable((String) session.getAttribute(KEY_SUCCESS_MESSAGE.getValue())).orElseThrow(
            SuccessMessageNotFoundException::new);
        model.addAttribute(KEY_IS_SUCCESS.getValue(), Boolean.TRUE);
        model.addAttribute(KEY_SUCCESS_MESSAGE.getValue(), message);
        session.setAttribute(KEY_IS_SUCCESS.getValue(), Boolean.FALSE);
        session.removeAttribute(KEY_SUCCESS_MESSAGE.getValue());
    }
}
