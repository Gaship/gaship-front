package shop.gaship.gashipfront.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.gaship.gashipfront.security.basic.handler.LoginSuccessHandler;
import shop.gaship.gashipfront.security.basic.service.CustomEmployeeUserDetailService;

/**
 * 직원들의 로그인을 위한 Security config입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityEmployeeConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final LoginSuccessHandler loginSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().maximumSessions(1);

        http.formLogin()
                .defaultSuccessUrl("/")
                .loginPage("/manager/login")
                .loginProcessingUrl("/manager/login")
                .successHandler(loginSuccessHandler)
                .usernameParameter("id")
                .passwordParameter("pw")
                .failureUrl("/manager-login")
            .and()
            .httpBasic()
            .and()
                .requestMatchers()
                    .antMatchers("/admin/**")
                    .antMatchers("/manager/**")
            .and();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider(null));
    }

    /**
     * 직원들의 로그인에 대한 Provider 설정하는 스프링 빈입니다.
     *
     * @param customUserDetailService 쇼핑몰 서버에서 직원의 로그인에 필요한 정보를 가져오는 Service입니다.
     * @return 로그인의 시도
     */
    @Bean
    public AuthenticationProvider authenticationProvider(
        CustomEmployeeUserDetailService customUserDetailService) {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthenticationProvider;
    }
}
