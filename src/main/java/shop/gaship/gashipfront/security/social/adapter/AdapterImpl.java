package shop.gaship.gashipfront.security.social.adapter;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.social.dto.Member;
import shop.gaship.gashipfront.security.social.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.NaverUserData;
import shop.gaship.gashipfront.security.social.dto.PaycoAccessToken;


@Component
public class AdapterImpl implements Adapter {
    @Override
    public NaverAccessToken requestNaverAccessToken(String uri) {
        WebClient webClient
            = WebClient.builder()
            .baseUrl(uri)
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .build();

        NaverAccessToken token = webClient.get()
            .retrieve()
            .bodyToMono(NaverAccessToken.class)
            .block();

        WebClient.ResponseSpec spec = webClient.get()
            .retrieve();

        ResponseEntity<?> response = spec.toEntity(NaverAccessToken.class).block();

        response.getBody();
        return token;
    }

    @Override
    public NaverUserData requestNaverUserData(String apiUrlForUserData, String accessToken) {
        WebClient webClient
            = WebClient.builder()
            .baseUrl(apiUrlForUserData)
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("Authorization", Strings.concat("Bearer ", accessToken))
            .build();

        return webClient.get().retrieve().bodyToMono(NaverUserData.class).block();
    }

    @Override
    public PaycoAccessToken requestPaycoAccessToken(String uri) {
        return null;
    }

    @Override
    public void requestPaycoInfo() {

    }

    @Override
    public Member requestMemberByMobile(String mobile) {
        WebClient webClient
            = WebClient.builder()
            .baseUrl("http://localhost:7072/security/mobile")
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .build();

        return webClient.get()
            .uri(uri -> uri.queryParam("mobile", mobile).build())
            .retrieve()
            .bodyToMono(Member.class)
            .blockOptional().orElseThrow(() -> new RuntimeException());
    }
}
