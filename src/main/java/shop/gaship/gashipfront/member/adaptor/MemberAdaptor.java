package shop.gaship.gashipfront.member.adaptor;

import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.exceptions.RequestFailureThrow;
import shop.gaship.gashipfront.member.dto.EmailPresence;
import shop.gaship.gashipfront.member.dto.FindMemberEmailRequest;
import shop.gaship.gashipfront.member.dto.FindMemberEmailResponse;
import shop.gaship.gashipfront.member.dto.MemberCreationRequest;
import shop.gaship.gashipfront.member.dto.MemberNumberPresence;
import shop.gaship.gashipfront.member.dto.ReissuePasswordRequest;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * 회원과 관련하여 데이터를 요청하기위한 adaptor 객체입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberAdaptor {
    private final String gatewayBaseurl;

    /**
     * 쇼핑몰 서버에 회원가입을 요청하는 메서드입니다.
     *
     * @param memberCreationRequest 쇼핑몰의 멤버로 가입할 정보입니다.
     * @return 회원가입이 정상적으로 완료시 true를 반환합니다.
     * @throws shop.gaship.gashipfront.exceptions.RequestFailureThrow 네트워크 혹은 웹 클라이언트의 오류를 던집니다.
     */
    public boolean signUpRequest(MemberCreationRequest memberCreationRequest) {
        WebClient.create(gatewayBaseurl).post()
            .uri("/api/members/sign-up")
            .bodyValue(memberCreationRequest)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
        return true;
    }

    /**
     * 이미 이메일이 존재하는지에 대한 여부를 쇼핑몰 서버에 요청하는 메서드입니다.
     *
     * @param email : 확인할 이메일
     * @return 이메일이 존재하는지에대한 결과를 담은 객체를 반환합니다.
     * @throws shop.gaship.gashipfront.exceptions.RequestFailureThrow 네트워크 혹은 웹 클라이언트의 오류를 던집니다.
     */
    public EmailPresence emailDuplicationCheckRequest(String email) {
        return WebClient.create(gatewayBaseurl).get()
            .uri("/api/members/check-email?email={email}", email)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(EmailPresence.class)
            .blockOptional()
            .orElseThrow(RequestFailureThrow::new)
            .getBody();
    }

    /**
     * 닉네임을 통한 회원 존재여부를 확인하기위해 쇼핑몰 서버에 요청하는 메서드입니다.
     *
     * @param nickName : 확인할 닉네임입니다.
     * @return MemberNumberPresence : 존재한다면 회원 고유번호가 담겨옵니다.
     * @throws RequestFailureThrow 네트워크 혹은 웹 클라이언트의 오류를 던집니다.
     */
    public MemberNumberPresence nicknameDuplicationCheckRequest(String nickName) {
        return WebClient.create(gatewayBaseurl).get()
            .uri("/api/members/check-nickname?nickanme={nickname}", nickName)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(MemberNumberPresence.class)
            .blockOptional()
            .orElseThrow(RequestFailureThrow::new)
            .getBody();
    }

    /**
     * 서버에 회원의 이메일이 무엇인지 질의합니다.
     *
     * @param  findMemberEmailRequest 회원의 닉네임이 들어있는 객체입니다.
     * @return 평문 데이터 중 일부 가려진 이메일 문자열을 반환합니다.
     * @throws RequestFailureThrow 네트워크 혹은 웹 클라이언트의 오류를 던집니다.
     */
    public String findUserEmailRequest(FindMemberEmailRequest findMemberEmailRequest) {
        return Objects.requireNonNull(WebClient.create(gatewayBaseurl).post()
                .uri("/api/members/find-email")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(findMemberEmailRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(FindMemberEmailResponse.class)
                .blockOptional()
                .orElseThrow(RequestFailureThrow::new)
                .getBody())
            .getObscuredEmail();
    }

    /**
     * 서버에 회원 임시비밀번호로 변경과 이메일에 임시비밀번호 발급을 요청합니다.
     *
     * @param reissuePasswordRequest 실명과 이메일 정보가 들어있는 객체입니다.
     * @return 임시비밀번호가 바뀌고 이메일에 잘 전송이되면 true값을 반환합니다.
     * @throws RequestFailureThrow 네트워크 혹은 웹 클라이언트의 오류를 던집니다.
     */
    public boolean reissuePasswordRequest(ReissuePasswordRequest reissuePasswordRequest) {
        String result = Objects.requireNonNull(WebClient.create(gatewayBaseurl).post()
                .uri("/api/members/find-password")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(reissuePasswordRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(Map.class)
                .blockOptional()
                .orElseThrow(RequestFailureThrow::new)
                .getBody())
            .get("changed")
            .toString();

        return Boolean.parseBoolean(result);
    }
}
