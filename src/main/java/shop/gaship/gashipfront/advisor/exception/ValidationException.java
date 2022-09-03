package shop.gaship.gashipfront.advisor.exception;

import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
public class ValidationException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public ValidationException(BindingResult bindingResult, HttpStatus status) {
        this.message = bindingResult.getAllErrors().stream()
            .map(objectError -> objectError.getDefaultMessage() + "\n")
            .collect(Collectors.joining());
        this.status = status;
    }
}
