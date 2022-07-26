package shop.gaship.gashipfront.security.social.manualitic.adapter.impl;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.social.manualitic.adapter.NaverAdapter;
import shop.gaship.gashipfront.security.social.manualitic.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.manualitic.dto.userdata.NaverUserData;

/**
 * NaverAdapter를 구현한 클래스입니다.
 *
 * @author : 최겸준
 * @see NaverAdapter
 * @since 1.0
 */

@Component
public class NaverAdapterImpl implements NaverAdapter {
    /**
     * 네이버에 accessToken을 요청하고 token을 받아오는 기능입니다.
     *
     * @param uri naver의 access token을 받기위해서 webclient로 전송할 url이며 내부에는 code, client_id, client_secret이 함께 있습니다.
     * @return naverAccessToken code값을 통해서 받아온 access token을  NaverAccessToken 타입으로 반환한 값을 담는 변수입니다.
     * @author 최겸준
     */
    @Override
    public NaverAccessToken requestNaverAccessToken(String uri) {
        WebClient naverWebClient
            = WebClient.builder()
            .baseUrl(uri)
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .build();

        return naverWebClient.get()
            .retrieve()
            .bodyToMono(NaverAccessToken.class)
            .block();
    }

    /**
     * access token을 가지고 네이버에 회원의 데이터를 요구하고 받아오는 기능입니다.
     *
     * @param apiUrlForUserData 회원의 데이터를 요구할때 요청할 url입니다.
     * @param accessToken 데이터를 요구하기전에 인가되었음을 증명해야하는 토큰입니다.
     * @return naverUserData 건네받은 유저정보를 보관하는 변수입니다.
     * @author 최겸준
     */
    @Override
    public NaverUserData requestNaverUserData(String apiUrlForUserData, String accessToken) {
        WebClient naverWebClient
            = WebClient.builder()
            .baseUrl(apiUrlForUserData)
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("Authorization", Strings.concat("Bearer ", accessToken))
            .build();

        return naverWebClient.get().retrieve().bodyToMono(NaverUserData.class).block();
    }

}
