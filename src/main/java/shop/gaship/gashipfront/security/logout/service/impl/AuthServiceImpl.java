package shop.gaship.gashipfront.security.logout.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.logout.adaptor.AuthAdaptor;
import shop.gaship.gashipfront.security.logout.service.AuthService;
import shop.gaship.gashipfront.security.social.common.dto.JwtDto;

/**
 * The type Auth service.
 */
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthAdaptor authAdaptor;

    @Override
    public ResponseEntity<?> logout() {
        JwtDto jwt = new JwtDto();
        String accessToken =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJyb2xlIjpbIlJPTEVfQURNSU4iXSwiaWQiOjEsImV4cGlyZUF0IjoxNjU4MTUzMzEyNTIzLCJjcmVhdGVBdCI6MTY1ODE1MTUxMjUyMywiZXhwIjoxNjU4MTUzMzEyfQ.O1m3GXRb3mM94lek2w1ZHyfAGJuN4sJ91WWrKTfAufxDUehsmvFAXip-mgZOO5fc";
        String refreshToken =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJyb2xlIjpbIlJPTEVfQURNSU4iXSwiaWQiOjEsImV4cGlyZUF0IjoxNjYwNzgxMjEyNjE4LCJjcmVhdGVBdCI6MTY1ODE1MTUxMjYxOCwiZXhwIjoxNjYwNzgxMjEyfQ.CDo6_rVB5_fLv4NAOcjp6ta0FKSEjsFYviKQmUMuaPwD5w8MtQ6en8Y0IgA3AE55";

        jwt.setAccessToken(accessToken);
        jwt.setRefreshToken(refreshToken);

        ResponseEntity<?> token = authAdaptor.logout(jwt);
        return token;
    }
}
