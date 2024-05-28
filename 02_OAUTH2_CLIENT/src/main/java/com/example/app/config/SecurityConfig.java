package com.example.app.config;

import com.example.app.config.auth.execptionHandler.CustomAcceessDeninedHandler;
import com.example.app.config.auth.execptionHandler.CustomAuthenticationEntryPoint;
import com.example.app.config.auth.loginHandler.CustomAuthenticationFailureHandler;
import com.example.app.config.auth.loginHandler.CustomLoginSuccessHandler;
import com.example.app.config.auth.logoutHandler.CustomLogoutHandler;
import com.example.app.config.auth.logoutHandler.CustomLogoutsuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //웹요청 처리
    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {

        //CSRF 비활성화
        http.csrf((config)->{config.disable();});
        //요청 URL별 접근 제한
        http.authorizeHttpRequests((auth)->{
            auth.requestMatchers("/","/join").permitAll();
            auth.requestMatchers("/user").hasRole("USER");
            auth.requestMatchers("/member").hasRole("MEMBER");
            auth.requestMatchers("/admin").hasRole("ADMIN");
            auth.anyRequest().authenticated();
        });

        //로그인 설정
        http.formLogin((login)->{
            login.permitAll();
            login.loginPage("/login");
            login.successHandler(new CustomLoginSuccessHandler());
            login.failureHandler(new CustomAuthenticationFailureHandler());
        });
        //로그아웃
        http.logout((logout)->{
            logout.permitAll();
            logout.logoutUrl("/logout");
            logout.addLogoutHandler(new CustomLogoutHandler());
            logout.logoutSuccessHandler(new CustomLogoutsuccessHandler());
        });
        //예외처리
        http.exceptionHandling((ex)->{
            ex.accessDeniedHandler(new CustomAcceessDeninedHandler());
            ex.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        });

        //Remember_me
        http.rememberMe((rm)->{
            rm.key("rememberMeKey");
            rm.rememberMeParameter("remember-me");
            rm.alwaysRemember(false);
            rm.tokenValiditySeconds(60*60);
            rm.tokenRepository(tokenRepository());
        });

        //Oauth2-client
        http.oauth2Login((oauth2)->{
            oauth2.loginPage("/login");
        });

        return http.build();


    }
    @Autowired
    private DataSource dataSource;
    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl rep=new JdbcTokenRepositoryImpl();
        rep.setDataSource(dataSource);
        return rep;
    }
    //가상유저 생성
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
//        InMemoryUserDetailsManager userDetailsManager =new InMemoryUserDetailsManager();
//        userDetailsManager
//                .createUser(User
//                        .withUsername("user")
//                        .password(passwordEncoder.encode("1234"))
//                        .roles("USER")
//                        .build()
//                );
//
//        userDetailsManager
//                .createUser(User
//                        .withUsername("member")
//                        .password(passwordEncoder.encode("1234"))
//                        .roles("MEMBER")
//                        .build()
//                );
//
//        userDetailsManager
//                .createUser(User
//                        .withUsername("admin")
//                        .password(passwordEncoder.encode("1234"))
//                        .roles("ADMIN")
//                        .build()
//                );
//
//        return userDetailsManager;
//    }

    //passwordEncoder 객체생성
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
