package com.example.shop.config;

import com.example.shop.config.handler.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final AuthenticationFailureHandler customFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/main/order", "/main/profile", "/main/mileage", "/main/address",
                        "/main/cart", "/main/payment", "/main/product/cart_ok").authenticated()
                .antMatchers("/main/index", "/main/category/**", "/main/product/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/main/login")
                .usernameParameter("loginId")
                .successHandler(successHandler())
                .failureHandler(customFailureHandler)


                .and() // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/main/index")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/main/restrict");

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler("/defaultUrl");
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }
}
