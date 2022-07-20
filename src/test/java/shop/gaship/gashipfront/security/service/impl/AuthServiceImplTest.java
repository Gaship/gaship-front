//package shop.gaship.gashipfront.security.service.impl;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import shop.gaship.gashipfront.security.dto.JwtTokenDto;
//import shop.gaship.gashipfront.security.service.AuthService;
//
///**
// * packageName    : shop.gaship.gashipfront.security.service.impl fileName       :
// * AuthServiceImplTest author         : jo date           : 2022/07/12 description    :
// * =========================================================== DATE              AUTHOR
// * NOTE ----------------------------------------------------------- 2022/07/12        jo       최초
// * 생성
// */
//@ExtendWith(SpringExtension.class)
//@Import(AuthServiceImpl.class)
//class AuthServiceImplTest {
//
//    @Autowired
//    AuthService service;
//
//    @DisplayName("로그아웃 성공")
//    @Test
//    void logout() {
//        // given
//        JwtTokenDto jwtTokenDto = new JwtTokenDto();
//        String accessToken = "abc123";
//        String refreshToken = "xyz321";
//
//        jwtTokenDto.setAccessToken(accessToken);
//        jwtTokenDto.setRefreshToken(refreshToken);
//
//        // when
//        ResponseEntity<?> logout = service.logout();
//
//        // then
//        assertThat(logout.getStatusCode()).isEqualTo(404);
//    }
//}