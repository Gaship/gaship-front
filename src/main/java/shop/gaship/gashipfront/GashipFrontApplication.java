package shop.gaship.gashipfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 메인 실행부입니다.
 *
 * @author 조재철
 * @since 1.0
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class GashipFrontApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(GashipFrontApplication.class, args);
    }
}
