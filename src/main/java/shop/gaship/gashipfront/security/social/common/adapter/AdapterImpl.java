package shop.gaship.gashipfront.security.social.common.adapter;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.social.dance.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dance.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.member.dto.Member;
import shop.gaship.gashipfront.security.social.common.dto.JwtDto;
import shop.gaship.gashipfront.security.social.common.dto.SigninSuccessUserDetailsDto;
import shop.gaship.gashipfront.security.social.common.util.ExceptionUtil;
import shop.gaship.gashipfront.security.social.common.exception.NullResponseBodyException;

/**
 * Adapter interface의 구현체입니다.
 *
 * @author : 최겸준
 * @see Adapter
 * @since 1.0
 */
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
            .baseUrl("http://localhost:7000")
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .build();

        return webClient.get()
            .uri("/members/email/{email}", email)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(Member.class)
            .blockOptional()
            .orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public void requestCreateMember(Member member) {
        WebClient.create("http://localhost:7000").post()
            .uri("/members?isOauth=true")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(member)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
    }

    @Override
    public Integer requestLastMemberNo() {
        WebClient webClient
            = WebClient.builder()
                .baseUrl("http://localhost:7000")
                .defaultHeader("Accept", MediaType.TEXT_PLAIN_VALUE)
                .build();

        return webClient.get()
            .uri("/members/lastNo")
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(Integer.class)
            .blockOptional()
            .orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public JwtDto requestJwt(SigninSuccessUserDetailsDto detailsDto) {
        WebClient webClient
            = WebClient.builder()
//            .baseUrl("http://localhost:7000")
            .baseUrl("http://192.168.0.2:7070")
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .build();

        return webClient.post()
            .uri("/securities/issue-token")
            .bodyValue(detailsDto)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(JwtDto.class)
            .blockOptional()
            .orElseThrow(NullResponseBodyException::new);
    }
}
