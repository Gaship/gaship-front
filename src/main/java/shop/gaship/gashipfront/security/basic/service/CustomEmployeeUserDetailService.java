package shop.gaship.gashipfront.security.basic.service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.config.ServerConfig;
import shop.gaship.gashipfront.exceptions.NoResponseDataException;
import shop.gaship.gashipfront.security.basic.dto.SignInUserDetailsDto;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * 직원 로그인시 직원정보를 불러오는 클래스입니다. Spring Security에서 사용됩니다.
 *
 * @author 김민수
 * @see UserDetailsService
 * @since 1.0
 */
@RequiredArgsConstructor
public class CustomEmployeeUserDetailService implements UserDetailsService {

    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    private static final String ERROR_MESSAGE = "정보가 존재하지않습니다.";

    private final ServerConfig serverConfig;

    @Override
    public UserDetailsDto loadUserByUsername(String username) throws UsernameNotFoundException {
        List<String> contentTypeValues = List.of(MediaType.APPLICATION_JSON.toString());

        SignInUserDetailsDto signInUserDetailsDto =
            WebClient.create(serverConfig.getGatewayUrl()).get()
                     .uri("/api/employees/user-detail?email={email}", username)
                     .headers(
                         httpHeaders -> httpHeaders.put(HttpHeaders.ACCEPT, contentTypeValues))
                     .retrieve()
                     .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                     .toEntity(SignInUserDetailsDto.class)
                     .timeout(TIMEOUT)
                     .blockOptional()
                     .orElseThrow(() -> new NoResponseDataException(ERROR_MESSAGE))
                     .getBody();

        return new UserDetailsDto(
            Objects.requireNonNull(signInUserDetailsDto).getEmail(),
            signInUserDetailsDto.getHashedPassword(),
            signInUserDetailsDto.getAuthorities(),
            signInUserDetailsDto.getMemberNo(),
            signInUserDetailsDto.getIsSocial());

    }
}
