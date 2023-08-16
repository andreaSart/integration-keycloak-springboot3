package com.example.kcIntegration17;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

/*
 we need to add a WebSecurityConfig class as follows to configure HttpSecurity 
 with OAuth2 Resource Server’s JWT authentication
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	public static final String ADMIN = "admin";
    public static final String USER = "user";
//    private JwtAuthConverter jwtAuthConverter;
    		
    		
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/test/anonymous").permitAll()
            .requestMatchers("/test/user*").hasRole(USER)
            .requestMatchers("/test/admin*").hasRole(ADMIN)
            .anyRequest().authenticated()
        )
		
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                
            )
        );
    return http.build();
	}
	
	private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
        return jwtConverter;
    }
	
	 class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
	    @Override
	    public Collection<GrantedAuthority> convert(Jwt jwt) {
	        final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
	        return ((List<String>)realmAccess.get("roles")).stream()
                    .map(roleName -> "ROLE_" + roleName) // prefix to map to a Spring Security "role"
	                .map(SimpleGrantedAuthority::new)
	                .collect(Collectors.toList());
	    }
	}


}
