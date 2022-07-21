package shop.gaship.gashipfront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.gaship.gashipfront.security.basic.handler.LoginSuccessHandler;
import shop.gaship.gashipfront.security.basic.service.CustomUserDetailService;
import shop.gaship.gashipfront.security.common.service.CommonService;
import shop.gaship.gashipfront.security.social.oauth2.handler.Oauth2LoginSuccessHandler;

/**
 * SpringSecurity에 관련한 전반적인 설정을 다루는 클래스입니다.
 *
 * @author 김민수
 * @author 최겸준
 * @author 조재철
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/manager")
            .hasRole("USER")
            .antMatchers("/all")
            .permitAll();

        http.formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/loginAction")
            .usernameParameter("id")
            .passwordParameter("pw")
            .defaultSuccessUrl("/all")
            .failureUrl("/login")
            .and()
            .logout();

        http.oauth2Login()
            .loginPage("/login")
            .defaultSuccessUrl("/all")
            .failureUrl("/login")
            .and();

        http.csrf().disable();
        http.logout().disable();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring()
            .antMatchers("/swagger-resources/**", "/swagger-ui.html", "/swagger/**");
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
        CustomUserDetailService customUserDetailService) {
        DaoAuthenticationProvider customDaoAuthenticationProvider = new DaoAuthenticationProvider();
        customDaoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        customDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return customDaoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public Oauth2LoginSuccessHandler oauth2LoginSuccessHandler(CommonService commonService) { return new Oauth2LoginSuccessHandler(commonService); }
}

