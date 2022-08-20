package shop.gaship.gashipfront.security.basic.handler;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.config.ServerConfig;
import shop.gaship.gashipfront.exceptions.NoResponseDataException;
import shop.gaship.gashipfront.security.basic.dto.SignInSuccessUserDetailsDto;
import shop.gaship.gashipfront.security.basic.dto.TokenRequestDto;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserInfoForJwtRequestDto;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * 자사, 관리자 로그인 성공시 사용할 success handler입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@RequiredArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final ServerConfig serverConfig;
    private final CartService cartService;
    private static final String CART_ID = "CID";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        SignInSuccessUserDetailsDto details =
            (SignInSuccessUserDetailsDto) authentication.getPrincipal();

        TokenRequestDto tokenRequestDto =
            new TokenRequestDto(
                details.getMemberNo().intValue(),
                details.getUsername(),
                details.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList())
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

        Integer memberNo = ((SignInSuccessUserDetailsDto) authentication.getPrincipal()).getMemberNo().intValue();
        // 비회원일 때 쓰던 장바구니 쿠키 값을 찾아서
        Optional<Cookie> nonMemberCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(CART_ID))
                .findFirst();
        // 찾은 쿠키값이 존재하면 비회원 때 담은 상품들을 회원의 장바구니에 넣는다.
        nonMemberCookie.ifPresent(cookie -> cartService.mergeCart(cookie.getValue(), memberNo));
        Cookie cookie = new Cookie(CART_ID, memberNo.toString());
        cookie.setMaxAge(60 * 60 * 24 * 100);
        response.addCookie(cookie);
    }
}
