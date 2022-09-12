package shop.gaship.gashipfront.advisor.controller;

import java.net.ConnectException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shop.gaship.gashipfront.exceptions.RequestFailureThrow;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ControllerAdvice
@Slf4j
public class ControllerAdvisor {
    private static FieldError apply(ObjectError objectError) {
        return (FieldError) objectError;
    }

    @ExceptionHandler(BindException.class)
    public String sendErrorMessage(BindException e, Model model) {
        String message = e.getAllErrors().stream()
            .map(ControllerAdvisor::apply)
            .map(objectError -> objectError.getField() + "은(는) "
                + objectError.getDefaultMessage() + "\n")
            .collect(Collectors.joining());

        model.addAttribute("message", message);
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());

        return "error/4xx";
    }

    @ExceptionHandler(RequestFailureThrow.class)
    public String handleRequestFailException(RequestFailureThrow e, Model model) {
        model.addAttribute("message", e.getMessage());
        model.addAttribute("status", e.getStatusCode().value());
        return "error/4xx";
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public String handleRequestFailException() {
        return "error/403";
    }
    
    @ExceptionHandler({ConnectException.class, Throwable.class})
    public String adaptorRefuse(Throwable e, Model model) {
        if(e instanceof ConnectException) {
            model.addAttribute("status", HttpStatus.BAD_GATEWAY.value());
        } else {
            return "error/500";
        }
        return "error/5xx";
    }
}
