package com.appsdevelopergroup.ws.api.resourceserver.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {

//    The AuthenticationPrincipal annotation is used to get all the claims into a single java object, that come from the token
//    So, basically what that annotation will do is to bind the currently authenticated user principal details, into the JWT object
//    From that JWT we can get the access token and the claims related.
    @GetMapping
    public Map<String, Object> getToken(@AuthenticationPrincipal Jwt jwt) {
        return Collections.singletonMap("principal", jwt);
    }
}
