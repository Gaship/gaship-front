package shop.gaship.gashipfront.security.social.adapter;

import java.util.Objects;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.jwt.JwtTokenDto;
import shop.gaship.gashipfront.security.social.dto.jwt.SignInSuccessUserDetailsDto;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.exception.ErrorResponse;
import shop.gaship.gashipfront.security.social.exception.JwtResponseException;

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
            .baseUrl("http://localhost:7071/securities/email")
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

    @Override
    public JwtTokenDto requestJwt(SignInSuccessUserDetailsDto detailsDto) {
        WebClient webClient
            = WebClient.builder()
            .baseUrl("http://localhost:7071/securities/issue-token")
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .build();

        ResponseEntity<Object> response = webClient.post()
            .bodyValue(detailsDto)
            .retrieve()
            .toEntity(Object.class)
            .block();

        // TODO think1 : 이게 adapter 단에 있는것이 맞는지 ? entity를 넘겨서 service에서 검증해야하는거 아닌지?
        if (!Objects.equals(response.getStatusCodeValue(), 201)) {
            String message = ((ErrorResponse) response.getBody()).getMessage();
            throw new JwtResponseException(message);
        }

        return (JwtTokenDto) response.getBody();
    }
}
