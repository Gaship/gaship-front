package shop.gaship.gashipfront.security.service;

import java.time.Duration;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.exceptions.NoResponseDataException;
import shop.gaship.gashipfront.security.dto.SignInUserDetailsDto;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * packageName    : shop.gaship.gashipauth.security <br/>
 * fileName       : CustomUserDetailService <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/10 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/10        김민수               최초 생성                 <br/>
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    private static final String ERROR_MESSAGE = "정보가 존재하지않습니다.";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<String> contentTypeValues = List.of(MediaType.APPLICATION_JSON.toString());
        return WebClient.create("http://localhost:7070").get()
                .uri("/members/retrieve/user-detail?email={email}", username)
                .headers(httpHeaders -> {
                    httpHeaders.put(HttpHeaders.CONTENT_TYPE, contentTypeValues);
                    httpHeaders.put(HttpHeaders.ACCEPT, contentTypeValues);
                })
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(SignInUserDetailsDto.class)
                .timeout(TIMEOUT)
                .blockOptional()
                .orElseThrow(() -> new NoResponseDataException(ERROR_MESSAGE))
                .getBody();
    }
}
