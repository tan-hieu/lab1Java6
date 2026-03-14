package com.fpoly.lab1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fpoly.lab1.service.DatabaseUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final DatabaseUserDetailsService databaseUserDetailsService;

    public SecurityConfig(DatabaseUserDetailsService databaseUserDetailsService) {
        this.databaseUserDetailsService = databaseUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(c -> c.disable())
            .cors(c -> c.disable())
            .userDetailsService(databaseUserDetailsService)
            .authorizeHttpRequests(config -> {
                // url2: cần ROLE_USER
                config.requestMatchers("/poly/url2").hasRole("USER");
                // url3: cần ROLE_ADMIN
                config.requestMatchers("/poly/url3").hasRole("ADMIN");
                // các /poly/** khác chỉ cần đăng nhập
                config.requestMatchers("/poly/**").authenticated();
                // còn lại cho truy cập tự do
                config.anyRequest().permitAll();
            })
            .formLogin(config -> {
                config.loginPage("/login/form");
                config.loginProcessingUrl("/login/check");
                config.defaultSuccessUrl("/", false);
                config.failureUrl("/login/failure");
                config.permitAll();
                config.usernameParameter("username");
                config.passwordParameter("password");
            })
            .rememberMe(config -> {
                config.tokenValiditySeconds(3 * 24 * 60 * 60);
                config.rememberMeCookieName("remember-me");
                config.rememberMeParameter("remember-me");
            })
            .logout(config -> {
                config.logoutUrl("/logout");
                config.logoutSuccessUrl("/login/exit");
                config.clearAuthentication(true);
                config.invalidateHttpSession(true);
                config.deleteCookies("remember-me");
            })
            .exceptionHandling(config -> {
                config.accessDeniedPage("/access-denied.html");
            });

        return http.build();
    }
}
