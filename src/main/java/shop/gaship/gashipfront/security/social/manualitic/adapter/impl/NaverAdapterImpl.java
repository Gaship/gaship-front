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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
