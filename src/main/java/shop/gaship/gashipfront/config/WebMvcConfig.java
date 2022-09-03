package shop.gaship.gashipfront.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.gaship.gashipfront.interceptor.ClientIpLogInterceptor;
import shop.gaship.gashipfront.interceptor.SessionInterceptor;

/**
 * 웹 MVC 초기 설정 클래스입니다.
 *
 * @author 김민수, 조재철
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final SessionInterceptor sessionInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/loaderio-3e0715e82d04b682a19347ed96bd23ef/").setViewName("loader");
        registry.addViewController("/all").setViewName("all");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ClientIpLogInterceptor clientIpLogInterceptor = new ClientIpLogInterceptor();

        registry.addInterceptor(clientIpLogInterceptor);

        registry.addInterceptor(sessionInterceptor);
    }
}
