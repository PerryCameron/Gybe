package com.ecsail.Gybe.configuration;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
/// added this in on 4/15/2023 to have better control over roles
@Component
public class LoggingAccessDecisionManager extends AffirmativeBased {

    // Example constructor injecting default voters
    public LoggingAccessDecisionManager() {
        super(Arrays.asList(
                new WebExpressionVoter(),  // Handles expressions like hasRole, hasAuthority
                new RoleVoter(),           // Votes based on role prefixes
                new AuthenticatedVoter()   // Votes if user is authenticated
        ));
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
        String method = fi.getRequest().getMethod();
        System.out.println("Access decision for URL: " + url + " Method: " + method + " with roles: " + configAttributes);
        super.decide(authentication, object, configAttributes);
    }
}
