package com.jinmlee.novel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/auth/**", "/CSS/**", "/JS/**", "/IMG/**", "/upload/**").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin((auth) -> auth.loginPage("/auth/login")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/",true)
                        .permitAll())
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                        .logoutSuccessUrl("/auth/login")
                        .invalidateHttpSession(true) // 로그아웃 할 때, 세션 종료
                        .deleteCookies("JSESSIONID") // 로그아웃 할 때. JSEESIONID 제거
                        .clearAuthentication(true) // 로그아웃 할 때, 권한 제거;
                ).csrf(AbstractHttpConfigurer::disable);



        return http.build();
    }
}
