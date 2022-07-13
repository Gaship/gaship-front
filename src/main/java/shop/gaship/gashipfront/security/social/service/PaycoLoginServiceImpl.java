package shop.gaship.gashipfront.security.social.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.social.dto.Member;
import shop.gaship.gashipfront.security.social.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.NaverUserData;

@Service
public class PaycoLoginServiceImpl implements LoginService {
    @Override
    public String getUriForLoginPageRequest() throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public NaverAccessToken getAccessToken(String code, String state) {
        return null;
    }

    @Override
    public NaverUserData getUserDataThroughAccessToken(String accessToken) {
        return null;
    }

    @Override
    public Member getMember(String mobile) {
        return null;
    }

    @Override
    public String requestJWT(String id, String email) {
        return null;
    }
}
