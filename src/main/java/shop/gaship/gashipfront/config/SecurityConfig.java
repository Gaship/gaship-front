package shop.gaship.gashipfront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.gaship.gashipfront.security.basic.service.CustomUserDetailService;
import shop.gaship.gashipfront.security.basic.handler.LoginSuccessHandler;

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

        http.sessionManagement().disable();
        http.formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/loginAction")
            .usernameParameter("id")
            .passwordParameter("pw")
            .defaultSuccessUrl("/all")
            .failureUrl("/login")
            .and()
            .logout();

//        http.oauth2Login()
//            .loginPage("/login")
//            .defaultSuccessUrl("/all")
//            .failureUrl("/login");

        http.csrf().disable();
//        http.logout().disable();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
        CustomUserDetailService customUserDetailService) {
        DaoAuthenticationProvider customDaoAuthenticationProvider = new DaoAuthenticationProvider();
        customDaoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        customDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return customDaoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user1")
            .password("$2a$10$pnrG3Vqknwc.Okcw9ab6A.S5lGjtw8UyDVd530Wwhi3GZA4V9nJVO")
            .roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }
}

