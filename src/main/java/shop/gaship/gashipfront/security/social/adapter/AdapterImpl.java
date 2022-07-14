package shop.gaship.gashipfront.security.social.adapter;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;

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
    public Member requestMemberByEmail(String email) {
        WebClient webClient
            = WebClient.builder()
            .baseUrl("http://localhost:7072/security/email")
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .build();

        return webClient.get()
            .uri(uri -> uri.queryParam("email", email).build())
            .retrieve()
            .bodyToMono(Member.class)
            .blockOptional().orElseThrow(() -> new RuntimeException());

        // TODO dummy3 : 테스트시에 위 주석으로 해야한다. 현재는 해당서버가 닫혀있고 다른 기능을 테스트하기위해서 더미객체를 둔다.
//        return new Member();
    }
}
