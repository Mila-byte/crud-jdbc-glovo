package com.glovoupgraded.config;

import com.glovoupgraded.entity.UserEntity;
import com.glovoupgraded.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers(toH2Console()).permitAll()
                                .requestMatchers("/", "/register").anonymous()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/home")
                                .permitAll()
                )
                .logout(logout -> {
                    try {
                        logout.permitAll()
                                .and()
                                .exceptionHandling()
                                .accessDeniedPage("/home");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .csrf((protection) -> protection
                        .ignoringRequestMatchers(toH2Console())
                )
                .headers((header) -> header
                        .frameOptions().sameOrigin()
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            UserEntity userEntity = userRepository.findByUsername(username);
            if (userEntity == null) {
                throw new UsernameNotFoundException("User not found: " + username);
            }
            return User.withUsername(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .roles("USER")
                    .build();
        };
    }
}
