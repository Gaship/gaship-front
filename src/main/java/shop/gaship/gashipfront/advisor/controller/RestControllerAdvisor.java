package shop.gaship.gashipfront.advisor.controller;

import java.net.ConnectException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipfront.cart.exception.ProductStockIsZeroException;
import shop.gaship.gashipfront.order.exception.CouponProcessException;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@RestControllerAdvice
public class RestControllerAdvisor {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> sendErrorMessage(MethodArgumentNotValidException e) {
        return e.getAllErrors().stream()
            .map(FieldError.class::cast)
            .collect(Collectors.toMap(FieldError::getField,
                fieldError -> Objects.requireNonNull(fieldError.getDefaultMessage())));
    }

    @ExceptionHandler({ConnectException.class, ProductStockIsZeroException.class,
        CouponProcessException.class, RuntimeException.class})
    public ResponseEntity<Map<String, String>> restAdaptorRefuse(Throwable e) {
        Map<String, String> errorMessage = Map.of("errorMessage", e.getMessage());

        if (e instanceof ConnectException) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorMessage);
        }

        return ResponseEntity.internalServerError().body(errorMessage);
    }
}
