package shop.gaship.gashipfront.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesRegistrationAdapter;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.security.basic.handler.LoginSuccessHandler;
import shop.gaship.gashipfront.security.basic.service.CustomUserDetailService;
import shop.gaship.gashipfront.security.common.gashipauth.service.AuthApiService;
import shop.gaship.gashipfront.security.social.automatic.handler.Oauth2LoginSuccessHandler;

/**
 * SpringSecurity에 관련한 전반적인 설정을 다루는 클래스입니다.
 *
 * @author 김민수
 * @author 최겸준
 * @author 조재철
 * @since 1.0
 */
@EnableWebSecurity
@Order(2)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_URI = "/login";

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers("/login", "/securities/login/naver",
                "/securities/login/naver/callback",
                "/oauth2/authorization/kakao",
                "/login/oauth2/code/kakao")
            .permitAll()
            .anyRequest().authenticated()
            .and();

        http.sessionManagement()
            .maximumSessions(1);

        http.formLogin()
                .loginPage(LOGIN_URI)
                .loginProcessingUrl("/loginAction")
                .usernameParameter("id")
                .passwordParameter("pw")
                .successHandler(loginSuccessHandler(null, null))
                .defaultSuccessUrl("/")
                .failureUrl(LOGIN_URI);

        http.logout();

        http.oauth2Login()
                .loginPage(LOGIN_URI)
                .successHandler(oauth2LoginSuccessHandler(null))
                .failureUrl(LOGIN_URI);

        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider(null));
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring()
            .antMatchers("/swagger-resources/**", "/swagger-ui.html", "/swagger/**");
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
        CustomUserDetailService customUserDetailService) {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler(ServerConfig serverConfig, CartService cartService) {
        return new LoginSuccessHandler(serverConfig, cartService);
    }

    @Bean
    public Oauth2LoginSuccessHandler oauth2LoginSuccessHandler(AuthApiService commonService, CartService cartService) {
        return new Oauth2LoginSuccessHandler(commonService, cartService);
    }
}

