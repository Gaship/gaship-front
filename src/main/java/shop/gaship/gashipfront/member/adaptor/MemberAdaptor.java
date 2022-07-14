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
 * description    : 회원과 관련하여 데이터를 요청하기위한 adaptor객체입니다.<br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/11           김민수               최초 생성                         <br/>
 */
@Component
@RequiredArgsConstructor
public class MemberAdaptor {
    private final String gatewayBaseurl;

    /**
     * 쇼핑몰 서버에 회원가입을 요청하는 메서드입니다.
     *
     * @param memberCreationRequest: 쇼핑몰의 멤버로 가입할 정보입니다.
     * @return boolean : 정상적으로 가입이 완료되면 true 반환합니다.
     */
    public boolean signUpRequest(MemberCreationRequest memberCreationRequest){
        WebClient.create(gatewayBaseurl).post()
            .uri("/members")
            .bodyValue(memberCreationRequest)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
        return true;
    }

    /**
     * 이미 이메일이 존재하는지에 대한 여부를 쇼핑몰 서버에 요청하는 메서드입니다.
     *
     * @param email : 확인할 이메일
     * @return EmailPresence : 이메일이 존재하는지에대한 결과를 담은 객체입니다.
     */
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

    /**
     * 닉네임을 통한 회원 존재여부를 확인하기위해 쇼핑몰 서버에 요청하는 메서드입니다,.
     *
     * @param nickName : 확인할 닉네임입니다.
     * @return MemberNumberPresence : 존재한다면 회원 고유번호가 담겨옵니다.
     */
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
