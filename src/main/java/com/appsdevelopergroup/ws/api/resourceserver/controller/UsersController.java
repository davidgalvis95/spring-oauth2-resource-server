package com.appsdevelopergroup.ws.api.resourceserver.controller;

import com.appsdevelopergroup.ws.api.resourceserver.response.UserRest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class UsersController {

    @GetMapping("/status/check")
    public String status() {
        return "Working...";
    }

//  The preauthorize annotation is much more flexible than the secured one. For example we can write expressions that will perform some behavior in the method
//  Also this annotation ca access the arguments that are passed through the method making some expressions
//  In this case we want to access the id and the jwt token that is passed in the web security configuration to compare them.
//  The jwt from the security config can be injected into the method, by using the @AuthenticationPrincipal and from there can be accessed in the @Preauthorized using the # operator
    @PreAuthorize("hasAuthority('ROLE_developer') or #id == #jwt.subject")
//    @Secured("ROLE_developer")
    @DeleteMapping("users/id")
    public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return "Deleting user " + id + "with subject id = " + jwt.getSubject();
    }

//  Basically this method is executed, but it will only return if the UserRest.userId is equal to the id that is passed through the jwt token in the security config
//  Otherwise it will be execute, but will return forbidden
    @PostAuthorize("returnObject.userid == #jwt.subject")
    @GetMapping("/id")
    public UserRest getUserDetails(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return new UserRest("David", "Galvis","");
    }
}
