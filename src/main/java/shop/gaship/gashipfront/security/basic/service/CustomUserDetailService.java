package shop.gaship.gashipfront.security.basic.service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.config.ServerConfig;
import shop.gaship.gashipfront.exceptions.NoResponseDataException;
import shop.gaship.gashipfront.security.basic.dto.SignInSuccessUserDetailsDto;
import shop.gaship.gashipfront.security.basic.exception.LoginDenyException;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * 자사로그인시 회원정보를 불러오는 클래스입니다. Spring Security에서 사용됩니다.
 *
 * @author 김민수
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @since 1.0
 */
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    private static final String ERROR_MESSAGE = "정보가 존재하지않습니다.";
    private static final String SOCIAL_LOGIN_DENY_MESSAGE =
        "소셜 회원가입 멤버는 일반 로그인이 불가능합니다. 소셜 로그인 서비스를 이용해주세요.";
    private final ServerConfig serverConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<String> contentTypeValues = List.of(MediaType.APPLICATION_JSON.toString());

        ResponseEntity<SignInSuccessUserDetailsDto> userDetailsResponse =
            WebClient.create(serverConfig.getGatewayUrl()).get()
                .uri("/api/members/user-detail?email={email}", username)
                .headers(httpHeaders -> {
                    httpHeaders.put(HttpHeaders.CONTENT_TYPE, contentTypeValues);
                    httpHeaders.put(HttpHeaders.ACCEPT, contentTypeValues);
                })
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(SignInSuccessUserDetailsDto.class)
                .timeout(TIMEOUT)
                .blockOptional()
                .orElseThrow(() -> new NoResponseDataException(ERROR_MESSAGE));

        SignInSuccessUserDetailsDto userDetails = userDetailsResponse.getBody();
        boolean isSocialMember = Objects.requireNonNull(userDetails).isSocial();

        if (isSocialMember) {
            throw new LoginDenyException(SOCIAL_LOGIN_DENY_MESSAGE);
        }

        return userDetails;
    }
}
