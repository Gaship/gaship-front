package shop.gaship.gashipfront.security.adaptor.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import shop.gaship.gashipfront.security.adaptor.AuthAdaptor;
import shop.gaship.gashipfront.security.dto.JwtTokenDto;
import shop.gaship.gashipfront.util.WebClientUtil;

/**
 * @author : 조재철
 * @since 1.0
 */
@Component
public class AuthAdaptorImpl implements AuthAdaptor {

    @Override
    public ResponseEntity<?> logout(JwtTokenDto jwtTokenDto) {
        Map<String, List<String>> headers = new HashMap<>();
        List<String> contentTypeValues = List.of(MediaType.APPLICATION_JSON.toString());
        headers.put("X-AUTH-TOKEN",
            List.of(jwtTokenDto.getAccessToken(), jwtTokenDto.getRefreshToken()));
        headers.put("Content-type", contentTypeValues);

        Map<String, String> body = new HashMap<>();

        return new WebClientUtil<String>()
            .post("http://localhost:7070",
                "/securities/logout",
                null,
                headers,
                body,
                String.class
            );
    }
}
