package shop.gaship.gashipfront.security.logout.service;

import org.springframework.http.ResponseEntity;

/**
 * The interface Auth service.
 */
public interface AuthService {

    /**
     * @return response entity
     */
    ResponseEntity<?> logout();
}
