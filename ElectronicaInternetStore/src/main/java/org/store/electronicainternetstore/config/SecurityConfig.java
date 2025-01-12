package org.store.electronicainternetstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()  {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailService userDetailsService() {
        return new UserDetailService();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/", "/about", "/payDelivery", "/contacts", "/registration", "/signin", "/editFirstProfile", "/saveUser", "/goToCategoryPage/**", "/searchItem", "/goToItemPage/**").permitAll()
                                .requestMatchers("/user/**").hasAuthority("ROLE_USER")
                                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form
                        .loginPage("/signin")
                        .loginProcessingUrl("/userLogin")
                        .defaultSuccessUrl("/")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    WebSecurityCustomizer configureWebSecurity() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/css/**", "/scripts/**");
    }
}
