package shop.gaship.gashipfront.security.social.service.signup;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dto.domain.MemberCreationRequest;

/**
 * @author : 최겸준
 * @see shop.gaship.gashipfront.security.social.service.signup.SignupService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {
    private final Adapter adapter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Boolean createMember(MemberCreationRequest memberCreationRequest) {
        memberCreationRequest.setPassword(passwordEncoder.encode(memberCreationRequest.getPassword()));
        return adapter.requestCreateMember(memberCreationRequest);
    }
}
