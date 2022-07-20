//package shop.gaship.gashipfront.service.impl;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import shop.gaship.gashipfront.security.logout.service.AuthService;
//import shop.gaship.gashipfront.security.logout.service.impl.AuthServiceImpl;
//
//@ExtendWith(SpringExtension.class)
//@Import(AuthServiceImpl.class)
//class LogoutServiceImplTest {
//
//    @Autowired
//    AuthService service;
//
//    @DisplayName("로그아웃 테스트")
//    @Test
//    void LogoutTest() {
//        // FIXME : service.logout() --> 로그아웃 테스트 경우 로그인 하고 해당 토큰 값을 가지고 난 후 테스트를 진행해야 하나?
//        // TODO : @InjectMock을 이 경우에 써야하나?
//    }
//}