package com.jw.resourceserver.controller.secured;

import com.jw.resourceserver.controller.BaseController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public abstract class BaseSecuredController extends BaseController {

    public static final String SECURED_API_PREFIX = BaseController.API_PREFIX + "/secure";

    protected JwtAuthenticationToken getJwtAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth;
        }

        return null;
    }

    @GetMapping("/userinfo")
    protected Map<String, Object> userInfo(@AuthenticationPrincipal final Jwt jwt) {
        Map<String, Object> response = new HashMap<>();
        response.put("userId", jwt.getSubject());
        response.put("scope", jwt.getClaimAsString("scope"));
        response.put("Claims", jwt.getClaims());
        response.put("Audience", jwt.getAudience());
        response.put("Issuer", jwt.getIssuer());
        response.put("IssuedAt", jwt.getIssuedAt());
        response.put("expiresAt", jwt.getExpiresAt());
        response.put("TokenValue", jwt.getTokenValue());

        return response;
    }

    @GetMapping("/userinfo2")
    protected Map<String, Object> userinfo2() {
        String userId = this.getJwtAuthentication().getName();
        Map<String, Object> tokenAttributes = this.getJwtAuthentication().getTokenAttributes();
        Jwt token = this.getJwtAuthentication().getToken();
        Collection<GrantedAuthority> authorities = this.getJwtAuthentication().getAuthorities();
        Object details = this.getJwtAuthentication().getDetails();

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("tokenAttributes", tokenAttributes);
        response.put("token", token);
        response.put("authorities", authorities);
        response.put("details", details);
        return response;
    }
}
