package shop.gaship.gashipfront.security;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.exceptions.NoResponseDataException;
import shop.gaship.gashipfront.security.dto.SignInSuccessUserDetailsDto;

/**
 * 회원의 정보를 쇼핑몰 서버에서 질의하기위해 정의한 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final String gatewayBaseUrl;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, List<String>> headers = new HashMap<>();
        List<String> contentTypeValues = List.of(MediaType.APPLICATION_JSON.toString());
        headers.put("Content-type", contentTypeValues);

        Map<String, String> body = new HashMap<>();
        body.put("email", username);

        return WebClient.create(gatewayBaseUrl).post()
            .uri("/members/email")
            .headers(httpHeaders -> httpHeaders.putAll(headers))
            .bodyValue(body)
            .retrieve()
            .toEntity(SignInSuccessUserDetailsDto.class)
            .timeout(Duration.ofSeconds(3))
            .blockOptional()
            .orElseThrow(() -> new NoResponseDataException("요청을 하였으나, 응답을 받는데 실패했습니다."))
            .getBody();
    }
}