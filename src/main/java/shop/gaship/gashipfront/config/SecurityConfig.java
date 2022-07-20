package shop.gaship.gashipfront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.gaship.gashipfront.security.CustomUserDetailService;
import shop.gaship.gashipfront.security.handler.LoginSuccessHandler;

/**
 * @author : 조재철
 * @since 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/member")
            .hasRole("USER")
            .antMatchers("/**")
            .permitAll()
            .and();

        http.sessionManagement().disable();

        http.formLogin()
            .successHandler(loginSuccessHandler())
            .successForwardUrl("/")
            .failureUrl("/login")
            .and();
//        http.oauth2Client();
        http.csrf().disable();

        http.logout().disable();
    }

//    @Override
//    public void configure(WebSecurity webSecurity) {
//        webSecurity.ignoring()
//            .antMatchers("/swagger-resources/**", "/swagger-ui.html", "/swagger/**");
//    }

    /**
     * @param custom user detail service
     * @return authentication provider
     * @author 조재철
     */
    @Bean
    public AuthenticationProvider authenticationProvider(
        CustomUserDetailService customUserDetailService) {
        DaoAuthenticationProvider customDaoAuthenticationProvider = new DaoAuthenticationProvider();
        customDaoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        customDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return customDaoAuthenticationProvider;
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }
}

