package io.pn.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
	
	@GetMapping("/token")
	public Map<String, Object> getPrincipal(@AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt){
		
		return Collections.singletonMap("Princlipal", jwt);
	}
}
