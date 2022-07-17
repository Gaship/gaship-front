package shop.gaship.gashipfront.security.social.service.signup;

import shop.gaship.gashipfront.security.social.dto.domain.MemberCreationRequest;

/**
 * 회원가입을 위한 service입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public interface SignupService {

    Boolean createMember(MemberCreationRequest memberCreationRequest);
}
