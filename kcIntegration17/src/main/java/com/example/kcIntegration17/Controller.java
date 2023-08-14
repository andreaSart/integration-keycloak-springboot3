package com.example.kcIntegration17;

import java.security.Principal;

//import javax.annotation.security.RolesAllowed;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/controller")
public class Controller {

	
	@GetMapping("/anonymous")
//	@RolesAllowed("user")
	public ResponseEntity<String> getAnonymousUser() {
		return ResponseEntity.ok("Hello anonymous");
	}
	
	@GetMapping("/user")
//	@RolesAllowed("user")
	public ResponseEntity<String> getUserUser(Principal principal) {
		JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
		Object user = token.getTokenAttributes().get("name");
		return ResponseEntity.ok("Hello user" );
	}
	
	@GetMapping("/admin")
//	@RolesAllowed("user")
	public ResponseEntity<String> getAdminUser(Principal principal) {
		JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
		Object user = token.getTokenAttributes().get("name");
		return ResponseEntity.ok("Hello admin" );
	}
	
}
