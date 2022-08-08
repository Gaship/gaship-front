package shop.gaship.gashipfront.config;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * 다국어 처리를 환경을 위해 설정하는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Configuration
public class LanguageConfig implements WebMvcConfigurer {
    /**
     * 언어 설정을 위한 기본위치, 쿠키를 통한 다국어 기능 처리를 위한 스프링 빈입니다.
     *
     * @return 쿠키를 통한 다국어 처리 객체 CookieLocaleResolver가 반환됩니다.
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.getDefault());
        cookieLocaleResolver.setCookieName("lang");
        return cookieLocaleResolver;
    }

    /**
     * 언어변경 요청시 언어(지역)변경 인터셉터를 통해 언어를 설정하는 스프링 빈입니다.
     *
     * @return 언어 변경 인터셉터가 반환됩니다.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");

        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
