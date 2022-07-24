package shop.gaship.gashipfront.security.logout.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.basic.dto.SignInUserDetailsDto;
import shop.gaship.gashipfront.security.logout.adaptor.AuthAdaptor;
import shop.gaship.gashipfront.security.logout.service.AuthService;
import shop.gaship.gashipfront.security.common.dto.JwtDto;

/**
 * The type Auth service.
 */
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthAdaptor authAdaptor;

    @Override
    public void logout(Integer memberNo, JwtDto jwt) {
        authAdaptor.logout(memberNo, jwt);
    }
}
