package shop.gaship.gashipfront.security.common.util;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import shop.gaship.gashipfront.exceptions.RequestFailureThrow;
import shop.gaship.gashipfront.member.dto.MemberAllFieldDto;
import shop.gaship.gashipfront.member.service.MemberService;
import shop.gaship.gashipfront.security.social.manualitic.dto.userdata.NaverUserDataResponse;

/**
 * 회원가입이 필요한지 필요하지 않은지를 비교해서 회원가입되었다면 가입된 member를 반환하며 가입되지 않았다면 자동가입시키는 역할을 가지는 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class SignupManager {
    public static final Integer MONTH = 0;
    public static final Integer DAY = 1;
    public static final String SOCIAL_NICKNAME_PREFIX = "사용자";
    private final MemberService memberService;

    /**
     * 400예외가 왔다면 회원가입시킨후 가입한 member를 가져오고 그렇지 않다면 가입된 회원을 받아서 반환하는 기능입니다.
     *
     * @param info 네이버에서 받은 유저정보가 있는 객체입니다.
     * @return Member 회원인 member객체를 요청한뒤 반환받은 member를 반환합니다.
     * @throws RequestFailureThrow RequestFailureException중에서도 statusCodeValue가 400이 아니면 예외를 발생합니다.
     *                             이 경우는 회원이 존재하지않아서 발생한 경우가 아니라 API 서버의 문제인 경우입니다.
     */
    public MemberAllFieldDto getMember(NaverUserDataResponse info) throws RequestFailureThrow {

        MemberAllFieldDto member = null;
        try {
            member = memberService.getMemberByEmail(info.getEmail());

        } catch (RequestFailureThrow e) {
            if (!e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                throw e;
            }
            return retryGetMember(info);
        }

        return member;
    }

    /**
     * 400예외가 왔다면 회원가입시킨후 가입한 member를 가져오고 그렇지 않다면 가입된 회원을 받아서 반환하는 기능입니다.
     *
     * @param email 다른 Oauth에서 받은 email 정보입니다.
     * @return Member 회원인 member객체를 요청한뒤 반환받은 member를 반환합니다.
     * @throws RequestFailureThrow RequestFailureException중에서도
     *                             statusCodeValue가 400이 아니면 예외를 발생합니다.
     *                             이 경우는 회원이 존재하지않아서 발생한 경우가 아니라 API 서버의 문제인 경우입니다.
     */
    public MemberAllFieldDto getMember(String email) throws RequestFailureThrow {
        MemberAllFieldDto member;

        try {
            member = memberService.getMemberByEmail(email);
        } catch (RequestFailureThrow e) {
            if (!e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                throw e;
            }
            return retryGetMember(email);
        }
        return member;
    }

    /**
     * 회원가입이 되어있지 않은 경우에 회원가입을 시키는 기능입니다.
     *
     * @param info 네이버에서 받은 유저정보가 있는 객체입니다.
     * @return Member 회원인 member객체를 요청한뒤 반환받은 member를 반환합니다.
     */
    private MemberAllFieldDto retryGetMember(NaverUserDataResponse info) {
        String email = info.getEmail();
        Integer memberNo = getLastMemberNo();
        String rowNickName = Strings.concat(SOCIAL_NICKNAME_PREFIX, String.valueOf(memberNo));
        MemberAllFieldDto member = buildMember(rowNickName, info);
        memberService.createMember(member);
        return memberService.getMemberByEmail(email);
    }

    /**
     * 회원가입이 되어있지 않은 경우에 회원가입을 시키는 기능입니다.
     *
     * @param email 다른 Oauth에서 받은 email 정보입니다.
     * @return Member 회원인 member객체를 요청한뒤 반환받은 member를 반환합니다.
     */
    private MemberAllFieldDto retryGetMember(String email) {
        Integer memberNo = getLastMemberNo();
        String rowNickName = Strings.concat(SOCIAL_NICKNAME_PREFIX, String.valueOf(memberNo));
        MemberAllFieldDto member = buildMember(rowNickName, email);
        memberService.createMember(member);
        return memberService.getMemberByEmail(email);
    }

    /**
     * 닉네임을 부여하기위해서 마지막 회원번호를 가져와서 하나 더한 값을 반환하는 기능입니다.
     * 이 기능은 사용자1, 사용자2 와같이 소셜로그인한 유저의 닉네임의 접미어로 사용됩니다.
     *
     * @return Integer 마지막번호 값에서 하나 더한 값입니다.
     */
    private Integer getLastMemberNo() {
        Integer num = memberService.getLastMemberNo();
        return ++num;
    }

    /**
     * 전달된 파라미터를 이용해서 가입시킬때 사용할 Member객체를 반환시키는 기능입니다.
     *
     * @param rowNickName 사용자라는 단어와 최근 가장 높은 번호를 가진 회원번호가 합쳐진 값입니다.
     * @param info        member객체를 만들때 필요한 필드들을 가지는 객체입니다.
     * @return Member 생성한 객체를 반환합니다.
     */
    private MemberAllFieldDto buildMember(String rowNickName, NaverUserDataResponse info) {
        MemberAllFieldDto member;
        String email = info.getEmail();
        String[] birthday = info.getBirthday().split("-");
        int year = Integer.parseInt(info.getBirthyear());
        int month = Integer.parseInt(birthday[MONTH]);
        int day = Integer.parseInt(birthday[DAY]);

        member = MemberAllFieldDto.builder().email(email).password(email)
            .nickName(Strings.concat(rowNickName, RandomStringUtils.random(16, true, true)))
            .name(info.getName()).gender(getGender(info)).phoneNumber(info.getPhoneNumber())
            .birthDate(LocalDate.of(year, month, day)).build();
        return member;
    }

    /**
     * 전달된 파라미터를 이용해서 가입시킬때 사용할 Member객체를 반환시키는 기능입니다.
     *
     * @param rowNickName 사용자라는 단어와 최근 가장 높은 번호를 가진 회원번호가 합쳐진 값입니다.
     * @param email       member객체를 만들때 필요한 email을 가지는 객체입니다.
     * @return Member 생성한 객체를 반환합니다.
     */
    private MemberAllFieldDto buildMember(String rowNickName, String email) {
        MemberAllFieldDto member;

        member = MemberAllFieldDto.builder().email(email).password(email)
            .nickName(Strings.concat(rowNickName, RandomStringUtils.random(16, true, true)))
            .name(email).build();
        return member;
    }

    private String getGender(NaverUserDataResponse info) {
        String gender = info.getGender();
        return gender.equals("M") ? "남" : "여";
    }
}
