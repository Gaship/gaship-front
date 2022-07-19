package shop.gaship.gashipfront.security.social.common.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.social.common.adapter.Adapter;
import shop.gaship.gashipfront.security.social.common.dto.Jwt;
import shop.gaship.gashipfront.security.social.common.dto.SigninSuccessUserDetailsDto;
import shop.gaship.gashipfront.security.social.common.service.CommonService;

/**
 * CommonService 구현체
 *
 * @author : 최겸준
 * @see CommonService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {
    private final Adapter adapter;

    @Override
    public Jwt getJWT(Integer memberNo, List<String> authorities) {
        SigninSuccessUserDetailsDto detailsDto = new SigninSuccessUserDetailsDto();
        detailsDto.setIdentifyNo(memberNo);
        detailsDto.setAuthorities(authorities);

        return adapter.requestJwt(detailsDto);
    }
}
