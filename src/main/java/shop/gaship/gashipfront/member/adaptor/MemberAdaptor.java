package shop.gaship.gashipfront.member.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.member.dto.EmailPresence;
import shop.gaship.gashipfront.member.dto.MemberCreationRequest;
import shop.gaship.gashipfront.member.dto.MemberNumberPresence;
import shop.gaship.gashipfront.member.exception.RequestFailureException;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * packageName    : shop.gaship.gashipfront.member.adaptor <br/>
 * fileName       : MemberAdaptor <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/11 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/11           김민수               최초 생성                         <br/>
 */
@Component
@RequiredArgsConstructor
public class MemberAdaptor {
    private final String gatewayBaseurl;

    public boolean signUpRequest(MemberCreationRequest memberCreationRequest){
        WebClient.create(gatewayBaseurl).post()
            .uri("/members")
            .bodyValue(memberCreationRequest)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
        return true;
    }

    public EmailPresence emailDuplicationCheckRequest(String email) {
        return WebClient.create(gatewayBaseurl).get()
            .uri("/members/retrieve?email={email}", email)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(EmailPresence.class)
            .blockOptional()
            .orElseThrow(RequestFailureException::new)
            .getBody();
    }

    public MemberNumberPresence nicknameDuplicationCheckRequest(String nickName){
        return WebClient.create(gatewayBaseurl).get()
            .uri("/members/retrieve?email={nickname}", nickName)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(MemberNumberPresence.class)
            .blockOptional()
            .orElseThrow(RequestFailureException::new)
            .getBody();
    }

}
