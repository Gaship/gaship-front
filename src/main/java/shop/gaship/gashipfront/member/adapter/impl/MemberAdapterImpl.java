package shop.gaship.gashipfront.member.adapter.impl;

import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.config.ServerConfig;
import shop.gaship.gashipfront.exceptions.RequestFailureThrow;
import shop.gaship.gashipfront.member.adapter.MemberAdapter;
import shop.gaship.gashipfront.member.dto.EmailPresence;
import shop.gaship.gashipfront.member.dto.MemberAllFieldDto;
import shop.gaship.gashipfront.member.dto.MemberNumberPresence;
import shop.gaship.gashipfront.member.dto.request.FindMemberEmailRequest;
import shop.gaship.gashipfront.member.dto.request.MemberCreationRequest;
import shop.gaship.gashipfront.member.dto.request.MemberModifyByAdminDto;
import shop.gaship.gashipfront.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipfront.member.dto.request.ReissuePasswordRequest;
import shop.gaship.gashipfront.member.dto.response.FindMemberEmailResponse;
import shop.gaship.gashipfront.member.dto.response.MemberResponseByAdminDto;
import shop.gaship.gashipfront.member.dto.response.MemberResponseDto;
import shop.gaship.gashipfront.security.common.exception.NullResponseBodyException;
import shop.gaship.gashipfront.util.ExceptionUtil;
import shop.gaship.gashipfront.util.dto.PageResponse;

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

    //TODO :URI 고쳐야해
    /**
     * gaship-shopping-mall api에 email을 통해서 member를 요청하는 기능입니다.
     *
     * @param email email을 저장하는 변수입니다.
     * @return responseEntity
     * @author 최겸준
     */
    @Override
    public MemberAllFieldDto requestMemberByEmail(String email) {
        return webClient.get()
            .uri("/members/email/{email}", email)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(MemberAllFieldDto.class)
            .blockOptional()
            .orElseThrow(NullResponseBodyException::new);
    }

    //TODO :URI 고쳐야해
    /**
     * 멤버의 회원가입 요청을 담당하는 기능입니다.
     *
     * @param member 회원가입시 필요한 정보를 담고있는 Memeber객체입니다.
     * @author 최겸준
     */
    @Override
    public void requestCreateMember(MemberAllFieldDto member) {
        webClient.post()
            .uri("/members?isOauth=true")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(member)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(void.class)
            .block();
    }

    //TODO :URI 고쳐야해
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

    //TODO :URI 고쳐야해
    /**
     *
     *{@inheritDoc}
     */
    @Override
    public MemberNumberPresence recommendMemberNoFind(String nickName) {
        return WebClient.create(serverConfig.getGatewayUrl()).get()
            .uri("/api/members/retrieve?nickname={nickname}", nickName)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(MemberNumberPresence.class)
            .blockOptional()
            .orElseThrow(RequestFailureThrow::new)
            .getBody();
    }

    /**
     * 쇼핑몰 서버에 회원가입을 요청하는 메서드입니다.
     *
     * @param memberCreationRequest 쇼핑몰의 멤버로 가입할 정보입니다.
     * @return 회원가입이 정상적으로 완료시 true를 반환합니다.
     * @throws shop.gaship.gashipfront.exceptions.RequestFailureThrow 네트워크 혹은 웹 클라이언트의 오류를 던집니다.
     */
    public boolean signUpRequest(MemberCreationRequest memberCreationRequest) {
        WebClient.create(serverConfig.getGatewayUrl()).post()
            .uri("/api/members/sign-up")
            .bodyValue(memberCreationRequest)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(Void.class)
            .block();

        return true;
    }

    /**
     * 이미 이메일이 존재하는지에 대한 여부를 쇼핑몰 서버에 요청하는 메서드입니다.
     *
     * @param email : 확인할 이메일
     * @return 이메일이 존재하는지에대한 결과를 담은 객체를 반환합니다.
     * @throws shop.gaship.gashipfront.exceptions.RequestFailureThrow 네트워크 혹은 웹 클라이언트의 오류를 던집니다.
     */
    @Override
    public EmailPresence emailDuplicationCheckRequest(String email) {
        return WebClient.create(serverConfig.getGatewayUrl()).get()
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
    @Override
    public MemberNumberPresence nicknameDuplicationCheckRequest(String nickName) {
        return WebClient.create(serverConfig.getGatewayUrl()).get()
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
        return Objects.requireNonNull(WebClient.create(serverConfig.getGatewayUrl()).post()
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
        String result = Objects.requireNonNull(WebClient.create(serverConfig.getGatewayUrl()).post()
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyMember(MemberModifyRequestDto request) {
        webClient.put().uri("/api/members/{memberNo}", request.getMemberNo())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyMemberByAdmin(MemberModifyByAdminDto request) {
        webClient.put().uri("/api/admin/members/{memberNo}", request.getMemberNo())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeMember(Integer memberNo) {
        webClient.delete().uri("/api/members/{memberNo}", memberNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberResponseDto findMember(Integer memberNo) {
        return webClient.get().uri("/api/members/{memberNo}", memberNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(MemberResponseDto.class)
                .blockOptional()
                .orElseThrow(NullResponseBodyException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberResponseByAdminDto findMemberByAdmin(Integer memberNo) {
        return webClient.get().uri("/api/admin/members/{memberNo}", memberNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(MemberResponseByAdminDto.class)
                .blockOptional()
                .orElseThrow(NullResponseBodyException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<MemberResponseByAdminDto> findMembers(Pageable pageable) {
        return webClient.get().uri(uriBuilder -> uriBuilder.path("/api/admin/members")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(
                    new ParameterizedTypeReference<PageResponse<MemberResponseByAdminDto>>() {})
            .blockOptional().orElseThrow(NullResponseBodyException::new);
    }
}
