package shop.gaship.gashipfront.security.basic.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.exceptions.NoResponseDataException;
import shop.gaship.gashipfront.security.basic.dto.SignInUserDetailsDto;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.util.ExceptionUtil;
import shop.gaship.gashipfront.security.basic.dto.TokenRequestDto;

/**
 * 자사로그인 성공시 사용할 success handler입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        SignInUserDetailsDto details =
            (SignInUserDetailsDto) authentication.getPrincipal();

        TokenRequestDto tokenRequestDto =
            new TokenRequestDto(
                details.getMemberNo(),
                details.getUsername(),
                details.getAuthorities()
            );

        JwtDto tokensResponse = WebClient.create("http://localhost:7070").post()
            .uri("/securities/issue-token")
            .bodyValue(tokenRequestDto)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(JwtDto.class)
            .blockOptional()
            .orElseThrow(() -> new NoResponseDataException(""))
            .getBody();

        HttpSession session = request.getSession();
        session.setAttribute("jwt", tokensResponse);
        // TODO 값 잘 가져와지는지 역직렬화를 잘하는지 ?
//        String access = session.getAttribute("jwt").getAccessToken();
    }
}
