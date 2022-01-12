package com.appsdevelopergroup.ws.api.resourceserver.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

//THis securedEnabled, enables or disables the @Secured annotation
//The prePostEnabled, enables the @PreAuthorize and @PostAuthorize
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//This EnableWebSecurity has included the @Configuration annotation
@EnableWebSecurity
@Slf4j
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http.authorizeRequests()
//                the way these roles are provider depends on the way the identity server provides the role in the access token, and might involve a spring converter
//                such as the KeycloakRoleConverter, so that we can get access to the specific roles, if we try to get access to a role without knowing the exact part in the json token where that is stored
//                the application won't be able to get the roles, and we'll receive a 403
                .antMatchers(HttpMethod.GET, "/users/status/check").hasRole("developer")//.hasAuthority("SCOPE_profile")
                .anyRequest()
                .authenticated()
                .and()
//                This will enable the comparison of the bearer token as well as the comparison and validation against the auth server
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);
    }
}
