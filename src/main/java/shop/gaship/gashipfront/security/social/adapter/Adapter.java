package shop.gaship.gashipfront.security.social.adapter;

import org.springframework.http.ResponseEntity;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.jwt.SignInSuccessUserDetailsDto;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;

public interface Adapter {
    NaverAccessToken requestNaverAccessToken(String uri);
    NaverUserData requestNaverUserData(String apiUrlForUserData, String accessToken);
    Member requestMemberByEmail(String mobile);
    ResponseEntity<Object> requestJwt(SignInSuccessUserDetailsDto detailsDto);
}
