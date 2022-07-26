package shop.gaship.gashipfront.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import shop.gaship.gashipfront.member.dto.MemberAllFieldDto;
import shop.gaship.gashipfront.member.dto.MemberCreationRequest;
import shop.gaship.gashipfront.member.dto.MemberNumberPresence;

/**
 * 회원가입을 위한 service레이어의 클래스입니다.
 *
 * @author 최겸준
 * @author 김민수
 * @since 1.0
 */
public interface MemberService {
    /**
     * 회원정보를 쇼핑몰API에서 조회합니다.
     *
     * @param email 조회할 조건이 되는 값입니다.
     * @return 조회한 회원에 대한 member객체입니다.
     * @author 최겸준
     */
    MemberAllFieldDto getMemberByEmail(String email);

    /**
     * 회원가입을 요청하는 기능입니다.
     *
     * @param member 요청할때 필요한 값들을 담은 객체입니다.
     * @author 최겸준
     */
    void createMember(MemberAllFieldDto member);

    /**
     * 닉네임값에 병합하기위해서 가장 최근 가입한 회원의 회원번호에서 1 더한 값을 받아오는 기능입니다.
     *
     * @return 최신 회원 + 1 값을 반환합니다.
     * @author 최겸준
     */
    Integer getLastMemberNo();


    boolean executeSignUp(MemberCreationRequest memberCreationRequest);

    boolean checkDuplicatedEmail(String email);

    boolean checkDuplicatedNickname(String nickname);

    MemberNumberPresence findRecommendMemberNo(String nickname);
}
