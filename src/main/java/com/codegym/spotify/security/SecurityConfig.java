package com.codegym.spotify.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .requestMatchers("/login", "/assets/**", "/", "/index", "/register/**", "/forbidden").permitAll()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index?success")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/index")
                        .permitAll()
                );
        http.authorizeRequests()
                .requestMatchers("/admin/**")
                .access("hasAnyRole('ROLE_ADMIN')");

        http.authorizeRequests()
                        .requestMatchers("/artist/edit/**", "/artist/new", "/artist/delete/**", "/own-artist")
                                .access("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')");

        http.authorizeRequests()
                .requestMatchers("/albums/new/**", "/albums/edit/**", "/album/delete/**")
                .access("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')");

        http.authorizeRequests()
                .requestMatchers("/song/delete/**", "/*/songs/upload")
                .access("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')");

        http.authorizeRequests()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/forbidden");

//        http.authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/login", "/assets/**", "/", "/index", "/register/**", "/forbidden").permitAll()
//                                .requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
//                                .requestMatchers("/artist/edit/**", "/artist/new", "/artist/delete/**", "/own-artist").hasAnyRole("ROLE_MODERATOR", "ROLE_ADMIN")
//                                .requestMatchers("/albums/new/**", "/albums/edit/**", "/album/delete/**").hasAnyRole("ROLE_MODERATOR", "ROLE_ADMIN")
//                                .requestMatchers("/song/delete/**", "/*/songs/upload").hasAnyRole("ROLE_MODERATOR", "ROLE_ADMIN")
//                                .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/index?success")
//                        .loginProcessingUrl("/login")
//                        .failureUrl("/login?error=true")
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                        .logoutSuccessUrl("/index")
//                        .permitAll()
//                )
//                .exceptionHandling()
//                .accessDeniedPage("/forbidden");
        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
