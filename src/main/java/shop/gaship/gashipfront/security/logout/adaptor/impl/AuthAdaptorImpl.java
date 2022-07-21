package shop.gaship.gashipfront.security.logout.adaptor.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.logout.adaptor.AuthAdaptor;
import shop.gaship.gashipfront.security.common.dto.JwtDto;

/**
 * @author 조재철
 * @since 1.0
 */
@Component
public class AuthAdaptorImpl implements AuthAdaptor {
    @Override
    public ResponseEntity<String> logout(JwtDto jwtDto) {
        Map<String, List<String>> headers = new HashMap<>();
        List<String> contentTypeValues = List.of(MediaType.APPLICATION_JSON.toString());
        headers.put("X-AUTH-TOKEN",
            List.of(jwtDto.getAccessToken(), jwtDto.getRefreshToken()));
        headers.put("Content-type", contentTypeValues);

        Map<String, String> body = new HashMap<>();

        WebClient webClient = WebClient.builder()
            .baseUrl("http://192.168.0.2:7070")
            .build();

//        return new WebClientUtil<String>()
//            .post("http://192.168.0.2:7070",
//                "/securities/logout",
//                null,
//                headers,
//                body,
//                String.class
//            );

        return webClient.post().uri("/securities/logout").retrieve().toEntity(String.class).block();
    }
}
