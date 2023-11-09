package com.ecsail.Gybe.service;

//import org.springframework.security.config.annotation.web.configurers.PathRequest;

//@EnableWebSecurity
public class SecurityConfig {
//
//    private final UserDetailsService userDetailsService;
//
//    // Constructor injection is preferred over field injection
//    public SecurityConfig(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // You can customize this part as per your requirements
//                        .invalidSessionUrl("/login?invalid")
//                        .sessionFixation().migrateSession()
//                        .maximumSessions(1)
//                        .expiredUrl("/login?expired")
//                        .maxSessionsPreventsLogin(false)
//                )
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/","/auth/**","/static/**").permitAll()
//                        .anyRequest().authenticated() // All other requests require authentication
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login") // specify your custom login page
//                        .permitAll() // allow everyone to see the login page
//                );
////                .httpBasic(Customizer.withDefaults());
//
//        // Additional configurations can be added here
//
//        return http.build();
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//

}
