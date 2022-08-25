package shop.gaship.gashipfront.security.social.manualitic.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import shop.gaship.gashipfront.security.social.manualitic.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.manualitic.dto.userdata.NaverUserData;


/**
 * Oauth Dance를 통해 naver에 로그인할시에 controller에서 사용될 service입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public interface NaverLoginService {
    /**
     * 로그인을 요청하는 uri를 만들어서 반환하는 기능입니다. csrf 방어도 함께합니다.
     *
     * @return 전체 uri입니다.
     * @throws UnsupportedEncodingException URLEncoder.encode시 발생할수 있는 예외입니다.
     */
    String getUriForLoginPageRequest()
        throws IOException, URISyntaxException, UnrecoverableKeyException, CertificateException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException;

    /**
     * naver에서 accessToken을 반환받는 기능을 합니다.
     *
     * @param code 인가코드로서 해당 코드를 제출해야지 accessToken을 요청할수 있습니다.
     * @param parameterState 해당 사용자에게 요청할때 저장한 값을 해당 사용자가 들고왔는지 확인하는 용도로 사용합니다.
     * @param redisState 실제 세션 또는 레디스세션에 저장된 state 값입니다.
     * @return accesstoken을 반환합니다.
     */
    NaverAccessToken getAccessToken(String code, String parameterState, String redisState);

    /**
     * naver에서 accessToken을 가지고 회원정보를 조회하는 기능입니다.
     *
     * @param accessToken 회원정보 조회를 위해 사용할 토큰입니다.
     * @return 얻어온 정보를 저장하는 dto 객체입니다.
     */
    NaverUserData getUserDataThroughAccessToken(String accessToken);
}
