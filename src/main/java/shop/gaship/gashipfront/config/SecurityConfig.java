package shop.gaship.gashipfront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.gaship.gashipfront.security.basic.service.CustomUserDetailService;
import shop.gaship.gashipfront.security.basic.handler.LoginSuccessHandler;
import shop.gaship.gashipfront.security.social.common.service.CommonService;
import shop.gaship.gashipfront.security.social.oauth2.handler.Oauth2LoginSuccessHandler;

/**
 * packageName    : shop.gaship.gashipfront.configure <br/>
 * fileName       : SecurityConfig <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/11 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/11           김민수               최초 생성                         <br/>
 */
@Configuration
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
            .successHandler(oauth2LoginSuccessHandler(null));

        http.csrf().disable();
//        http.logout().disable();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailService customUserDetailService) {
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

