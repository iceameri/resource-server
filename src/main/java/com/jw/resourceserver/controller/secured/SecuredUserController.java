package com.jw.resourceserver.controller.secured;

import com.jw.resourceserver.dto.security.AuthenticatedUser;
import com.jw.resourceserver.dto.security.UserPrincipal;
import com.jw.resourceserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = SecuredUserController.BASE_PATH)
public class SecuredUserController extends BaseSecuredController {

    public static final String BASE_PATH = BaseSecuredController.SECURED_API_PREFIX + "/user";

    private final UserService userService;

    public SecuredUserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userinfo")
    public ResponseEntity<String> userInfo(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        if (userPrincipal.isAuthenticated()) {
            AuthenticatedUser user = (AuthenticatedUser) userPrincipal;

            return ResponseEntity.ok(String.format("안녕하세요, %s 님", user.getUsername()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/userinfo2")
    public Map<String, Object> userInfo2(@AuthenticationPrincipal final Jwt jwt) {
        Map<String, Object> response = new HashMap<>();
        response.put("email", jwt.getClaimAsString("email"));
        response.put("sub", jwt.getSubject());
        response.put("scope", jwt.getClaimAsString("scope"));
        return response;
    }
}
