package com.ecsail.Gybe.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class LoggingAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();

        // Log the access decision details
        System.out.println("Access decision for URL: " + url + " Method: " + method);

        // Implement your authorization logic here
        boolean granted = authentication.get().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));  // Example logic

        // Log the decision
        System.out.println("Access Granted: " + granted);

        // Return the decision
        return new AuthorizationDecision(granted);
    }
}

