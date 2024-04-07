package com.ecsail.Gybe.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService detailsService) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(detailsService);
        return new ProviderManager(daoProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/css/**",
                            "/images/**",
                            "/js/**",
                            "/renew/**",
                            "/register/**",
                            "/error/**",
                            "/email-error/**",
                            "/bod/**",
                            "/bod-stripped/**",
                            "/slip-wait-list/**",
                            "/stats/**",
                            "/slips/**",
                            "/slips-in-template/**",
                            "/upsert_user/**"
                    ).permitAll();
                    auth.requestMatchers("/home/**").hasRole("USER");
                    auth.requestMatchers("/admin/**","/adduser").hasAuthority("ROLE_ADMIN"); // Only 'ROLE_ADMIN' can access '/admin/**'
                    auth.requestMatchers("/rb_roster/**","/Rosters/**").hasAnyRole("ADMIN","MEMBERSHIP");
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> form
                        .loginPage("/login") // Specify your custom login page URL
                        .permitAll() // Allow all users to access the login page
                        .successHandler((request, response, authentication) -> {
                            SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
                            if (savedRequest == null) {
                                response.sendRedirect(request.getContextPath() + "/");
                            } else {
                                String targetUrl = savedRequest.getRedirectUrl();
                                response.sendRedirect(targetUrl);
                            }
                        })
                        .failureUrl("/login?error=true")) // Redirect to log
                .logout(logout -> logout
                        .logoutUrl("/logout") // Specify the logout URL
                        .logoutSuccessUrl("/renew") // URL to redirect after logout
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Delete session cookies
                        .clearAuthentication(true)) // Clear authentication
                .build();
    }
}
