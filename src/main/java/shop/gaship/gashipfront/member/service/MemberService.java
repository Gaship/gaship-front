package shop.gaship.gashipfront.member.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.member.dto.MemberAllFieldDto;
import shop.gaship.gashipfront.member.dto.MemberNumberPresence;
import shop.gaship.gashipfront.member.dto.request.MemberCreationRequest;
import shop.gaship.gashipfront.member.dto.request.MemberModifyByAdminDto;
import shop.gaship.gashipfront.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipfront.member.dto.response.MemberResponseByAdminDto;
import shop.gaship.gashipfront.member.dto.response.MemberResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 회원가입을 위한 service레이어의 클래스입니다.
 *
 * @author 최겸준
 * @author 김민수
 * @author 최정우
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

    /**
     * 회원이 회원의 정보를 수정하는 메서드.
     *
     * @param request 수정하려는 정보가 담긴 dto
     * @author 최정우
     */
    void modifyMember(MemberModifyRequestDto request);

    /**
     * 관리자가 회원의 상태를 수정하는 메서드.
     *
     * @param request 수정하려는 정보가 담긴 dto
     * @author 최정우
     */
    void modifyMemberByAdmin(MemberModifyByAdminDto request);

    /**
     * 관리자가 회원의 정보를 삭제하는 메서드.
     *
     * @param memberNo 회원의 id
     * @author 최정우
     */
    void removeMember(Integer memberNo);

    /**
     * 회원이 회원의 정보를 조회하는 메서드.
     *
     * @param memberNo 회원의 id
     * @return 회원의 정보를 리턴합니다.
     * @author 최정우
     */
    MemberResponseDto findMember(Integer memberNo);

    /**
     * 관리자가 회원의 리스트를 조회하는 메서드.
     *
     * @param pageable 페이지의 번호와 사이즈가 담겨있는 객체
     * @return 회원의 정보들을 리턴합니다.
     * @author 최정우
     */
    PageResponse<MemberResponseByAdminDto> findMembers(Pageable pageable);

    /**
     * 관리자가 회원의 정보를 조회하는 메서드.
     *
     * @param memberNo 회원의 id
     * @return 회원의 정보를 리턴합니다.
     * @author 최정우
     */
    MemberResponseByAdminDto findMemberByAdmin(Integer memberNo);

    /**
     * 이메일인증을 허가요청을 하는 메서드입니다.
     *
     * @param verifyCode 인증코드입니다.
     */
    void requestApproveEmailVerification(String verifyCode);

    /**
     * 이메일인증을 확인하는 메서드입니다.
     *
     * @param verifyCode 인증코드입니다.
     */
    void checkApprovedEmail(String verifyCode);
}
