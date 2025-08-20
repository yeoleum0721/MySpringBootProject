package com.rookies4.myspringboot.security.config;

import com.rookies4.myspringboot.security.service.UserInfoUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity  //Authentication (인증) 활성화
@EnableMethodSecurity //Authorization (권한) 활성화
public class SecurityConfig {
    @Bean
    //패스워드 암호화
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //UserDetailsService 인터페이스를 구현한 객체를 Bean으로 설정하기
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //UserDetailsService Bean을 설정하기
        authenticationProvider.setUserDetailsService(userDetailsService());
        //BCryptPasswordEncoder Bean을 설정하기
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

//    @Bean
//    //authentication(인증)을 위한 User 생성 (관리자, 일반사용자)
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        //관리자 생성
//        UserDetails admin = User.withUsername("adminboot")
//                .password(encoder.encode("pwd1"))
//                .roles("ADMIN")
//                .build();
//        //일반사용자 생성
//        UserDetails user = User.withUsername("userboot")
//                .password(encoder.encode("pwd2"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  //csrf 기능 비활성화
                http.csrf(csrf -> csrf.disable())
                        //요청별로 권한을 설정
                        .authorizeHttpRequests(auth -> {
                            // api/users/welcome 경로는 인증 없이 접근가능함
                            auth.requestMatchers("/api/users/welcome","/userinfos/new").permitAll()
                                    //  api/users/welcome 경로는 인증이 반드시 필요함
                                    .requestMatchers("/api/users/**").authenticated();
                        })
                        // form로그인 페이지는 스프링이 디폴트로 제공하는 페이지를 사용하겠다.
                        .formLogin(withDefaults())
                        .build();
    }
}