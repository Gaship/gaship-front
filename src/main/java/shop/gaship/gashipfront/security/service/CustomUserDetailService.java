package shop.gaship.gashipfront.security.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.dto.SignInSuccessUserDetailsDto;
import shop.gaship.gashipfront.util.WebClientUtil;

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
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, List<String>> headers = new HashMap<>();
        List<String> contentTypeValues = List.of(MediaType.APPLICATION_JSON.toString());
        headers.put("Content-type", contentTypeValues);

        Map<String, String> body = new HashMap<>();
        body.put("email", username);

        return (UserDetails) new WebClientUtil<SignInSuccessUserDetailsDto>()
                .post("http://localhost:7072",
                    "/members/email",
                    null,
                    headers,
                    body,
                    SignInSuccessUserDetailsDto.class
                    );
    }
}
