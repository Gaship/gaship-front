package shop.gaship.gashipfront.security;

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
 * @author : 조재철
 * @since 1.0
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
