package shop.gaship.gashipfront.security.common.member.adapter;

import shop.gaship.gashipfront.member.dto.EmailPresence;
import shop.gaship.gashipfront.member.dto.MemberCreationRequest;
import shop.gaship.gashipfront.member.dto.MemberNumberPresence;
import shop.gaship.gashipfront.member.exception.RequestFailureException;
import shop.gaship.gashipfront.security.common.member.dto.MemberDto;

/**
 * api서버에 요청을 처리하는 기능을 담당하는 interface입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public interface MemberAdapter {
    /**
     * gaship-shopping-mall api에 email을 통해서 member를 요청하는 기능입니다.
     *
     * @param email email을 저장하는 변수입니다.
     * @return responseEntity
     * @author 최겸준
     */
    MemberDto requestMemberByEmail(String email);

    /**
     * 멤버의 회원가입 요청을 담당하는 기능입니다.
     *
     * @param member 회원가입시 필요한 정보를 담고있는 Memeber객체입니다.
     * @author 최겸준
     */
    void requestCreateMember(MemberDto member);

    /**
     * 멤버의 회원가입시 닉네임생성을 위해 최신 번호를 가져오는 기능입니다.
     *
     * @author 최겸준
     */
    Integer requestLastMemberNo();

    /**
     * 쇼핑몰 서버에 회원가입을 요청하는 메서드입니다.
     *
     * @param memberCreationRequest 쇼핑몰의 멤버로 가입할 정보입니다.
     * @return 회원가입이 정상적으로 완료시 true를 반환합니다.
     * @throws RequestFailureException 네트워크 혹은 웹 클라이언트의 오류를 던집니다.
     */
    boolean signUpRequest(MemberCreationRequest memberCreationRequest);

    /**
     * 이미 이메일이 존재하는지에 대한 여부를 쇼핑몰 서버에 요청하는 메서드입니다.
     *
     * @param email : 확인할 이메일
     * @return 이메일이 존재하는지에대한 결과를 담은 객체를 반환합니다.
     * @throws RequestFailureException 네트워크 혹은 웹 클라이언트의 오류를 던집니다.
     */
    EmailPresence emailDuplicationCheckRequest(String email);

    /**
     * 닉네임을 통한 회원 존재여부를 확인하기위해 쇼핑몰 서버에 요청하는 메서드입니다.
     *
     * @param nickName : 확인할 닉네임입니다.
     * @return MemberNumberPresence : 존재한다면 회원 고유번호가 담겨옵니다.
     * @throws RequestFailureException 네트워크 혹은 웹 클라이언트의 오류를 던집니다.
     */
    MemberNumberPresence nicknameDuplicationCheckRequest(String nickName);
}
