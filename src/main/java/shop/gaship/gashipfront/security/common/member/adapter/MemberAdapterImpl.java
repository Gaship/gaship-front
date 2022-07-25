package shop.gaship.gashipfront.security.common.member.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.config.ServerConfig;
import shop.gaship.gashipfront.member.dto.EmailPresence;
import shop.gaship.gashipfront.member.dto.MemberCreationRequest;
import shop.gaship.gashipfront.member.dto.MemberNumberPresence;
import shop.gaship.gashipfront.member.exception.RequestFailureException;
import shop.gaship.gashipfront.security.common.exception.NullResponseBodyException;
import shop.gaship.gashipfront.security.common.member.dto.MemberDto;
import shop.gaship.gashipfront.security.common.util.ExceptionUtil;

/**
 * ShoppingMallAPIAdapter interface의 구현체입니다.
 *
 * @author : 김민수
 * @author : 최겸준
 * @see MemberAdapter
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberAdapterImpl implements MemberAdapter {
    private final ServerConfig serverConfig;
    private final WebClient webClient;

    /**
     * gaship-shopping-mall api에 email을 통해서 member를 요청하는 기능입니다.
     *
     * @param email email을 저장하는 변수입니다.
     * @return responseEntity
     * @author 최겸준
     */
    @Override
    public MemberDto requestMemberByEmail(String email) {
        return webClient.get()
            .uri("/members/email/{email}", email)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(MemberDto.class)
            .blockOptional()
            .orElseThrow(NullResponseBodyException::new);
    }

    /**
     * 멤버의 회원가입 요청을 담당하는 기능입니다.
     *
     * @param member 회원가입시 필요한 정보를 담고있는 Memeber객체입니다.
     * @author 최겸준
     */
    @Override
    public void requestCreateMember(MemberDto member) {
        webClient.post()
            .uri("/members?isOauth=true")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(member)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(void.class)
            .block();
    }

    /**
     * 멤버의 회원가입시 닉네임생성을 위해 최신 번호를 가져오는 기능입니다.
     *
     * @author 최겸준
     */
    @Override
    public Integer requestLastMemberNo() {
        return webClient.get()
            .uri("/members/lastNo")
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(Integer.class)
            .blockOptional()
            .orElseThrow(NullResponseBodyException::new);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean signUpRequest(MemberCreationRequest memberCreationRequest) {
        WebClient.create(serverConfig.getGatewayUrl()).post()
            .uri("/members")
            .bodyValue(memberCreationRequest)
            .retrieve()
            .onStatus(HttpStatus::isError, shop.gaship.gashipfront.util.ExceptionUtil::createErrorMono);
        return true;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public EmailPresence emailDuplicationCheckRequest(String email) {
        return WebClient.create(serverConfig.getGatewayUrl()).get()
            .uri("/members/retrieve?email={email}", email)
            .retrieve()
            .onStatus(HttpStatus::isError, shop.gaship.gashipfront.util.ExceptionUtil::createErrorMono)
            .toEntity(EmailPresence.class)
            .blockOptional()
            .orElseThrow(RequestFailureException::new)
            .getBody();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public MemberNumberPresence nicknameDuplicationCheckRequest(String nickName) {
        return WebClient.create(serverConfig.getGatewayUrl()).get()
            .uri("/members/retrieve?email={nickname}", nickName)
            .retrieve()
            .onStatus(HttpStatus::isError, shop.gaship.gashipfront.util.ExceptionUtil::createErrorMono)
            .toEntity(MemberNumberPresence.class)
            .blockOptional()
            .orElseThrow(RequestFailureException::new)
            .getBody();
    }

    @Override
    public MemberNumberPresence recommendMemberNoFind(String nickName) {
            return WebClient.create(serverConfig.getGatewayUrl()).get()
                .uri("/members/retrieve?nickname={nickname}", nickName)
                .retrieve()
                .onStatus(HttpStatus::isError, shop.gaship.gashipfront.util.ExceptionUtil::createErrorMono)
                .toEntity(MemberNumberPresence.class)
                .blockOptional()
                .orElseThrow(RequestFailureException::new)
                .getBody();
    }
}
