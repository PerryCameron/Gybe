package com.ecsail.Gybe.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

@Configuration
public class SecurityConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Value("${security.rememberme.key}")
    private String rememberMeKey;

    @Autowired
    public SecurityConfiguration(LoggingAuthorizationManager loggingAuthorizationManager) {
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService detailsService) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(detailsService);
        daoProvider.setPasswordEncoder(passwordEncoder()); // Set the password encoder
        return new ProviderManager(daoProvider);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Spring Security evaluates rules in the order they are defined. If a rule that permits broader access
        // (permitAll() or authenticated()) is processed before a more restrictive rule (hasRole(), hasAuthority()),
        // it can lead to unintended access.
        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/actuator/**", "/adduser").hasAuthority("ROLE_ADMIN"); // Only 'ROLE_ADMIN' can access '/admin/**'
                    auth.requestMatchers("/rb_roster/**", "/Rosters/**").hasAnyRole("ADMIN", "MEMBERSHIP");

                    auth.requestMatchers(
                            "/css/**", "/images/**", "/js/**", "/renew/**", "/register/**",
                            "/error/**", "/email-error/**", "/bod/**", "/bod-stripped/**",
                            "/slip-wait-list/**", "/stats/**", "/slips/**", "/slips-in-template/**",
                            "/upsert_user/**", "/update_creds/**", "/update_password/**", "/login/**",
                            "/access-denied/**"
                    ).permitAll();

                    auth.requestMatchers("/**", "/chart/**").hasRole("USER");
                // auth.requestMatchers("/secured-endpoint/**").access(loggingAuthorizationManager);
                    auth.anyRequest().authenticated(); // ensures that any request not matched by prior rules must be authenticated, regardless of the user’s role
                })
                // a custom AccessDeniedHandler to handle cases where users try to access resources they are not
                // permitted to access. This handler allows you to customize the response, whether it’s redirecting
                // the user to a specific page, returning a custom error message, or logging the incident.
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                // this was recently added to customize session management
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Create a session if needed
                        .invalidSessionUrl("/login?invalid") // Redirect to login page for invalid sessions
                        .maximumSessions(3) // Allow only 3 sessions per user
                        .sessionRegistry(sessionRegistry())  // Ensure session registry is properly configured if used
                        // a strict policy where new logins are prevented if maximum sessions limit is reached
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl("/login?expired")) // Redirect to login page for expired sessions
                .rememberMe(r -> r
                        // The key acts as a shared secret between the application and the cookies, used to sign
                        // and verify the remember-me cookie. It helps prevent tampering and forgery of the cookie,
                        // as only cookies generated with the correct key will be considered valid.
                        .key(rememberMeKey)
                        .tokenValiditySeconds(604800) // cookie to last 7 days
                        // When you set .useSecureCookie(true) in your Spring Security configuration,
                        // you are telling the framework to add the Secure attribute to the remember-me
                        // cookie. This attribute is a flag included in HTTP cookies that tells the browser
                        // to send the cookie only in requests initiated over secure (HTTPS) protocols.
                        .useSecureCookie(true))
                .formLogin(form -> form
                        // the login behavior to redirect users to either their originally requested URL or
                        // the home page upon successful authentication, which enhances the user experience by
                        // making the transition seamless
                        .loginPage("/login")
                        // The permitAll() method in the form login configuration might seem redundant as it
                        // is already applied globally to the login page URL. However, it's a common practice
                        // to explicitly specify it in the form login configuration for clarity and to avoid
                        // accidental security misconfigurations if the global rules change.
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            try {
                                // The SavedRequest object represents a request that was saved during the
                                // authentication process. This typically occurs when a user tries to access
                                // a protected resource and is redirected to the login page. SavedRequest saves
                                // the original request so that, after successful authentication, the user can be
                                // redirected back to their intended destination.
                                //  -------------------------------------------------
                                // HttpSessionRequestCache: This is a utility class provided by Spring Security that
                                // is used to retrieve the SavedRequest from the session. This class interacts with
                                // the session to save and retrieve requests that are interrupted by authentication needs.
                                SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
                                if (savedRequest == null) {
                                    String redirectUrl = request.getContextPath() + "/";
                                    response.sendRedirect(redirectUrl);
                                    log.debug("No saved request, redirecting to home page: " + redirectUrl);
                                } else {
                                    String targetUrl = savedRequest.getRedirectUrl();
                                    response.sendRedirect(targetUrl);
                                    log.debug("Redirecting to saved request URL: " + targetUrl);
                                }
                            } catch (Exception e) {
                                log.error("Error during login success handling", e);
                            }
                        })
                        .failureUrl("/login?error=true")) // Redirect to log
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/logout")  // Disable CSRF protection specifically for the logout URL
                )
                .logout(logout -> logout
                        // This sets the URL that triggers the logout process. When a request is made to this URL,
                        // Spring Security will intercept it and commence the logout procedure.
                        .logoutUrl("/logout")
                        // after successfully logging out, users are redirected to the URL specified here. In this
                        // case, users are redirected to /renew. This can be used to guide users to a page that
                        // confirms they have been logged out or to offer other post-logout actions like re-login
                        // or feedback.
                        .logoutSuccessUrl("/login")
                        // This ensures that the current HTTP session is invalidated when the logout is successful.
                        // Invalidating the session is a security best practice, as it prevents session fixation
                        // attacks and ensures that any session data is cleaned up.
                        .invalidateHttpSession(true)
                        // This command instructs Spring Security to delete the "JSESSIONID" cookie from the
                        // client's browser. "JSESSIONID" is the default cookie used by Java servlets to
                        // manage sessions. Deleting this cookie ensures that the session cannot be reused
                        // even if the session ID remains in the user's browser.
                        .deleteCookies("JSESSIONID")
                        // This setting ensures that the authentication is cleared from the security context,
                        // which effectively ensures that the user is no longer considered authenticated.
                        // It helps prevent the previous user’s authentication from affecting subsequent
                        // users or sessions.
                        .clearAuthentication(true)); // Clear authentication
        return http.build();
    }
}
