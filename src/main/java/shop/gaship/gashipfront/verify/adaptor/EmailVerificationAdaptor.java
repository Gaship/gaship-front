package shop.gaship.gashipfront.verify.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.member.exception.RequestFailureException;
import shop.gaship.gashipfront.util.ExceptionUtil;
import shop.gaship.gashipfront.verify.dto.RequestSuccessDto;

/**
 * 이메일 인증을 서버로부터 요청하기 위한 adaptor입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class EmailVerificationAdaptor {
    private final String gatewayBaseurl;

    public RequestSuccessDto requestVerificationEmail(String address){
        return WebClient.create(gatewayBaseurl).get()
            .uri("/securities/verify/email?address={address}", address)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(RequestSuccessDto.class)
            .blockOptional()
            .orElseThrow(RequestFailureException::new)
            .getBody();
    }

    public RequestSuccessDto verifyEmailByVerificationCode(String verificationCode){
        return WebClient.create(gatewayBaseurl).get()
            .uri("/securities/verify/email/{verificationCode}", verificationCode)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(RequestSuccessDto.class)
            .blockOptional()
            .orElseThrow(RequestFailureException::new)
            .getBody();
    }
}
