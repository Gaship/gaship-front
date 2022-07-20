package shop.gaship.gashipfront.security.service;

import org.springframework.http.ResponseEntity;

/**
 * @author : 조재철
 * @since 1.0
 */
public interface AuthService {

    /**
     * 로그아웃 관련 메서드 입니다.
     *
     * @return response entity
     * @author 조재철
     */
    ResponseEntity<?> logout();
}
