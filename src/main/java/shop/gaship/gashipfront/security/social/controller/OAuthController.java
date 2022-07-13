package shop.gaship.gashipfront.security.social.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.domain.UserDetailsDto;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.service.dance.NaverLoginService;

@Controller
@RequestMapping("/securities")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {
    private final NaverLoginService naverLoginServiceImpl;

    @GetMapping("/login/naver")
    @ResponseBody
    public ResponseEntity<String> redirectUriForLoginPageRequestNaver(HttpServletResponse response) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        String uriForLoginPageRequest = naverLoginServiceImpl.getUriForLoginPageRequest();
        headers.setLocation(URI.create(uriForLoginPageRequest));
        log.debug("uriForLoginPageRequest : {}", uriForLoginPageRequest);

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping(value = "/login/naver/callback", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAccessTokenAndAuthenticateNaver(String code, String state) throws Exception {
        // TODO think5 : 굳이 클래스를 만들 필요가 있었는지? accesstoken 값을 로그인할때는 한번만 사용하고 더이상 사용할 계획이 없는데
        NaverAccessToken naverAccessToken = naverLoginServiceImpl.getAccessToken(code, state);

        // TODO think7 : 회원정보를 이용할일이 없어서 email만 받으면 되는데 클래스가 필요할까?
        NaverUserData data = naverLoginServiceImpl.getUserDataThroughAccessToken(naverAccessToken.getAccessToken());
        Member member = naverLoginServiceImpl.getMember(data.getResponse().getEmail());

        // TODO need3 : service단으로 옮기기 refactoring
        List<String> authorities = new ArrayList<>();
        authorities.add("ROLE_" + "USER"); // TODO need4 : USER를 실제 member 값으로 권한 대체하기 현재는 더미데이터로 member가 왔을거라 가정하고 만들고 있음
        member.setAuthorities(authorities);
        member.setPassword("1234");
        member.setEmail(data.getResponse().getEmail());
        UserDetailsDto userDetailsDto = new UserDetailsDto(member.getEmail(), member.getPassword(), member.getAuthorities().stream()
            .map(i -> new SimpleGrantedAuthority(i))
            .collect(Collectors.toList()), member);

        // TODO think6 : JWT 토큰발급하는 상황에서 굳이 jwt 발급요청뒤에 권한만 넘겨주면되는데 context에 값을 넣을 필요가 있을까?
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsDto, null,
            List.of(new SimpleGrantedAuthority("ROLE_USER")));
        context.setAuthentication(authentication);
        return "/all";
    }
}
