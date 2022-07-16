package shop.gaship.gashipfront.security.social.service.common;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.jwt.JwtTokenDto;
import shop.gaship.gashipfront.security.social.dto.jwt.SignInSuccessUserDetailsDto;
import shop.gaship.gashipfront.security.social.util.ResponseEntityVerifier;

/**
 * packageName    : shop.gaship.gashipfront.security.social.service.common
 * fileName       : ShoppingmallServiceImpl
 * author         : choi-gyeom-jun
 * date           : 2022-07-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-14        choi-gyeom-jun       최초 생성
 */
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {
    private final Adapter adapter;

    @Override
    public Member getMemberByEmail(String email) {
        ResponseEntity<Object> response = adapter.requestMemberByEmail(email);
        ResponseEntityVerifier.verify(response, HttpStatus.OK);
        
        return (Member) response.getBody();
    }

    @Override
    public JwtTokenDto getJWT(Long identifyNo, List<String> authorities) throws Exception {
        SignInSuccessUserDetailsDto detailsDto
            = makeDetailsDto(identifyNo, authorities);

        ResponseEntity<Object> response = adapter.requestJwt(detailsDto);
        ResponseEntityVerifier.verify(response, HttpStatus.CREATED);

        return (JwtTokenDto) response.getBody();
    }

    private SignInSuccessUserDetailsDto makeDetailsDto(Long identifyNo,
                                                       List<String> authorities) {
        SignInSuccessUserDetailsDto detailsDto = new SignInSuccessUserDetailsDto();
        detailsDto.setIdentifyNo(identifyNo);
        detailsDto.setAuthorities(authorities);
        return detailsDto;
    }
}
