package shop.gaship.gashipfront.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
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
@Order(1)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CartService cartService;
    private static final String LOGIN_URI = "/login";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/**")
            .permitAll()
            .and();

        http.sessionManagement()
            .maximumSessions(1);

        http.formLogin()
                .loginPage(LOGIN_URI)
                .loginProcessingUrl(LOGIN_URI)
                .successHandler(loginSuccessHandler(null, null))
                .failureUrl(LOGIN_URI)
                .usernameParameter("id")
                .passwordParameter("pw")
                .and();

        http.oauth2Login()
                .loginPage(LOGIN_URI)
                .defaultSuccessUrl("/")
                .failureUrl(LOGIN_URI)
                .successHandler(oauth2LoginSuccessHandler(null, null));

        http.logout().disable();
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
    public LoginSuccessHandler loginSuccessHandler(
        ServerConfig serverConfig, CartService cartService) {
        return new LoginSuccessHandler(serverConfig, cartService);
    }

    @Bean
    public Oauth2LoginSuccessHandler oauth2LoginSuccessHandler(
        AuthApiService commonService, CartService cartService) {
        return new Oauth2LoginSuccessHandler(commonService, cartService);
    }

    @Bean
    public WebClient webClient(ServerConfig serverConfig) {
        return WebClient.builder()
            .baseUrl(serverConfig.getGatewayUrl())
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).build();
    }
}

