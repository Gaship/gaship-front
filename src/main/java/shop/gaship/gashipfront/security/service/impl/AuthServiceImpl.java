package shop.gaship.gashipfront.security.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.adaptor.AuthAdaptor;
import shop.gaship.gashipfront.security.dto.JwtTokenDto;
import shop.gaship.gashipfront.security.service.AuthService;
import shop.gaship.gashipfront.util.WebClientUtil;

/**
 * @author : 조재철
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthAdaptor authAdaptor;

    @Override
    public ResponseEntity<?> logout() {
        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJyb2xlIjpbIlJPTEVfQURNSU4iXSwiaWQiOjEsImV4cGlyZUF0IjoxNjU4MTUzMzEyNTIzLCJjcmVhdGVBdCI6MTY1ODE1MTUxMjUyMywiZXhwIjoxNjU4MTUzMzEyfQ.O1m3GXRb3mM94lek2w1ZHyfAGJuN4sJ91WWrKTfAufxDUehsmvFAXip-mgZOO5fc";
        String refreshToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJyb2xlIjpbIlJPTEVfQURNSU4iXSwiaWQiOjEsImV4cGlyZUF0IjoxNjYwNzgxMjEyNjE4LCJjcmVhdGVBdCI6MTY1ODE1MTUxMjYxOCwiZXhwIjoxNjYwNzgxMjEyfQ.CDo6_rVB5_fLv4NAOcjp6ta0FKSEjsFYviKQmUMuaPwD5w8MtQ6en8Y0IgA3AE55";

        jwtTokenDto.setAccessToken(accessToken);
        jwtTokenDto.setRefreshToken(refreshToken);

        return authAdaptor.logout(jwtTokenDto);
    }
}
