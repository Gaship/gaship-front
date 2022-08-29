package shop.gaship.gashipfront.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.gaship.gashipfront.interceptor.ClientIpLogInterceptor;

/**
 * 웹 MVC 초기 설정 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/all").setViewName("all");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ClientIpLogInterceptor clientIpLogInterceptor = new ClientIpLogInterceptor();

        registry.addInterceptor(clientIpLogInterceptor);
    }
}
