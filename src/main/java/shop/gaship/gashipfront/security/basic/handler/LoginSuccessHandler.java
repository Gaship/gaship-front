package shop.gaship.gashipfront.security.basic.handler;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.config.ServerConfig;
import shop.gaship.gashipfront.exceptions.NoResponseDataException;
import shop.gaship.gashipfront.security.basic.dto.TokenRequestDto;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * 자사, 관리자 로그인 성공시 사용할 success handler입니다.
 *
 * @author 김민
 * @since 1.0
 */
@RequiredArgsConstructor
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ServerConfig serverConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        UserDetailsDto details =
            (UserDetailsDto) authentication.getPrincipal();

        List<String> userAuthorities = details.getAuthorities().stream()
                                              .map(GrantedAuthority::getAuthority)
                                              .collect(Collectors.toList());
        TokenRequestDto tokenRequestDto =
            new TokenRequestDto(
                details.getMemberNo(),
                details.getUsername(),
                userAuthorities
            );

        JwtDto tokensResponse = WebClient.create(serverConfig.getGatewayUrl()).post()
                                         .uri("/securities/issue-token")
                                         .bodyValue(tokenRequestDto)
                                         .retrieve()
                                         .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                                         .toEntity(JwtDto.class)
                                         .blockOptional()
                                         .orElseThrow(() -> new NoResponseDataException(""))
                                         .getBody();

        HttpSession session = request.getSession();
        session.setAttribute("memberInfo", tokenRequestDto);
        session.setAttribute("jwt", tokensResponse);

        if (Objects.equals(request.getRequestURI(), "/login")) {
            response.sendRedirect("/");
            return;
        }

        response.sendRedirect("/admin");
    }
}
