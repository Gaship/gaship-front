package shop.gaship.gashipfront.config;

import lombok.RequiredArgsConstructor;
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
@EnableWebSecurity
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityEmployeeConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final LoginSuccessHandler loginSuccessHandler;
    private final ServerConfig serverConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/manager/login")
                .loginProcessingUrl("/manager/login")
                .successHandler(loginSuccessHandler)
                .usernameParameter("id")
                .passwordParameter("pw")
                .failureUrl("/manager/login")
            .and()
            .httpBasic()
            .and()
                .requestMatchers()
                .antMatchers("/admin/**")
                .antMatchers("/manager/**")
            .and()
                .authorizeRequests()
                .antMatchers("/admin/**")
                .hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                .and()
            .authenticationProvider(authenticationProvider());


        http.logout()
                .logoutUrl("/manager/logout")
                .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * 직원들의 로그인에 대한 Provider 설정하는 스프링 빈입니다.
     *
     * @return 로그인시 로그인 확인을 담당하는 객체가 반환됩니다.
     */
    public AuthenticationProvider authenticationProvider() {
        CustomEmployeeUserDetailService customEmployeeUserDetailService =
            new CustomEmployeeUserDetailService(serverConfig);
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customEmployeeUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthenticationProvider;
    }
}
