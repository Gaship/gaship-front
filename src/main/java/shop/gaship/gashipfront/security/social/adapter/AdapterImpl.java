package shop.gaship.gashipfront.security.social.adapter;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.jwt.SignInSuccessUserDetailsDto;
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
    public ResponseEntity<Object> requestMemberByEmail(String email) {
        WebClient webClient
            = WebClient.builder()
            .baseUrl("http://localhost:7071/securities/email")
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .build();

        return webClient.get()
            .uri(uri -> uri.queryParam("email", email).build())
            .retrieve()
            .toEntity(Object.class)
            .block();
    }

    @Override
    public ResponseEntity<Object> requestJwt(SignInSuccessUserDetailsDto detailsDto) {
        WebClient webClient
            = WebClient.builder()
            .baseUrl("http://localhost:7071/securities/issue-token")
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .build();

        return webClient.post()
            .bodyValue(detailsDto)
            .retrieve()
            .toEntity(Object.class)
            .block();
    }


}
