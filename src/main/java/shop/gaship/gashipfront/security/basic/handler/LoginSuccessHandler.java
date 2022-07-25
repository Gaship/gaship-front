package shop.gaship.gashipfront.security.basic.handler;

import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.exceptions.NoResponseDataException;
import shop.gaship.gashipfront.security.basic.dto.SignInSuccessUserDetailsDto;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserInfoForJwtRequestDto;
import shop.gaship.gashipfront.util.ExceptionUtil;

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
        SignInSuccessUserDetailsDto details =
            (SignInSuccessUserDetailsDto) authentication.getPrincipal();

        UserInfoForJwtRequestDto userInfo =
            new UserInfoForJwtRequestDto();

        userInfo.setMemberNo(details.getMemberNo().intValue());
        userInfo.setAuthorities(
            details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        JwtDto tokensResponse = WebClient.create("http://172.20.10.5:7070").post()
            .uri("/securities/issue-token")
            .bodyValue(userInfo)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(JwtDto.class)
            .blockOptional()
            .orElseThrow(() -> new NoResponseDataException(""))
            .getBody();

        HttpSession session = request.getSession();
        session.setAttribute("jwt", tokensResponse);
    }
}
