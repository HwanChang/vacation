package com.hwanchang.vacation.config;

import com.hwanchang.vacation.entity.user.Role;
import com.hwanchang.vacation.security.*;
import com.hwanchang.vacation.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Jwt jwt;

    private final JwtTokenConfig jwtTokenConfig;

    private final JwtAccessDeniedHandler accessDeniedHandler;

    private final EntryPointUnauthorizedHandler unauthorizedHandler;

    @Bean
    public JwtTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtTokenFilter(jwtTokenConfig.getHeader(), jwt);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs", "/webjars/**", "/static/**", "/templates/**", "/h2/**", "/h2-console/**");
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder builder, JwtProvider jwtProvider) {
        builder.authenticationProvider(jwtProvider);
    }

    @Bean
    public JwtProvider jwtAuthenticationProvider(Jwt jwt, UserService userService) {
        return new JwtProvider(jwt, userService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
            .headers()
                .disable()
            .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .authorizeRequests()
                .antMatchers("/api/auth").permitAll()
                .antMatchers("/api/v?/user/exists").permitAll()
                .antMatchers("/api/v?/user/join").permitAll()
                .antMatchers("/api/v?/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .disable();
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
