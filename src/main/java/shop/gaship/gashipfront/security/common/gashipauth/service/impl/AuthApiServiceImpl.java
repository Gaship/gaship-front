package shop.gaship.gashipfront.security.common.gashipauth.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserInfoForJwtRequestDto;
import shop.gaship.gashipfront.security.common.gashipauth.adapter.impl.AuthApiAdapter;
import shop.gaship.gashipfront.security.common.gashipauth.service.AuthApiService;

/**
 * CommonService 구현체.
 *
 * @author : 최겸준
 * @see AuthApiService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthApiServiceImpl implements AuthApiService {
    private final AuthApiAdapter adapter;

    @Override
    public JwtDto getJwt(Integer memberNo, List<String> authorities) {
        UserInfoForJwtRequestDto detailsDto = new UserInfoForJwtRequestDto();
        detailsDto.setMemberNo(memberNo);
        detailsDto.setAuthorities(authorities);

        return adapter.requestJwt(detailsDto);
    }

    @Override
    public void logout(Integer memberNo, JwtDto jwtDto) {
        adapter.requestLogout(memberNo, jwtDto);
    }
}
