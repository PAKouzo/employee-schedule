package vju.finalexam.project.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import vju.finalexam.project.security.services.CustomUserDetailsService;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPoint unauthorizedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .headers(h -> h.frameOptions(fo -> fo.sameOrigin()))
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(antMatcher("/")).permitAll();
                auth.requestMatchers(antMatcher("/ui/**")).permitAll();
                auth.requestMatchers(antMatcher("/error")).permitAll();
                auth.requestMatchers(antMatcher("/api/login")).permitAll();
                auth.requestMatchers(antMatcher("/api/logout")).authenticated();
                auth.requestMatchers(antMatcher("/api/**")).authenticated();
            });
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }
}