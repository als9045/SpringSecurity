package org.example.club.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

    @Bean
    //password encoding
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    //인증처리
    //InMemoryUserDetailsManager 메모리에 사용자 정보 저장
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("1111"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers("/sample/all").permitAll()
                        .requestMatchers("/sample/member").hasRole("USER")
                        .anyRequest().authenticated()
        )

//        // formLogin() 대신 새로운 방법을 사용해야 함
//        http
//                .formLogin(form -> form
//                        .loginPage("/all")  // 로그인 페이지 URL 설정
//                        .permitAll()          // 모든 사용자에게 로그인 페이지 접근 허용
//                )
//                .logout(logout -> logout
//                        .permitAll()  // 모든 사용자에게 로그아웃 페이지 접근 허용
//
//                )
                .csrf(cs -> cs.disable());


        return http.build();
    }

   }
