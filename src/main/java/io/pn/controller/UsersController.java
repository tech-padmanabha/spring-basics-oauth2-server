package io.pn.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UsersController {
	
	@GetMapping("/api/check")
	public String checkUser() {
		
		return "User Checked...!";
	}
	
//	@Secured("ROLE_developer")
//	@PreAuthorize("hasRole('developer')")
	@PreAuthorize("hasAuthority('ROLE_developer') or #id==#jwt.subject")
	
// by providing the id as subject argument we can authenticate
//	@PreAuthorize("#id==#jwt.subject")
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable String id,@AuthenticationPrincipal Jwt jwt) {
		
		return "User id "+id+" deleted ..! and Principle Sub:"+jwt.getSubject();
	}
	
	@PostAuthorize("returnObject.userId == #jwt.subject")
	@GetMapping("/get/{userId}")
	public UserRest getUser(@PathVariable String userId,@AuthenticationPrincipal Jwt jwt) {
		
		return new UserRest("Padm", "Sahu", "d38c9945-6467-4ca8-a160-d84a1fb47403");
	}
	
	@PreAuthorize("hasRole('developer')")
	@GetMapping("/api/get/{userId}")
	public UserRest getUser(@PathVariable String userId) {
		
		return new UserRest("Padm", "Sahu", userId.toUpperCase());
	}

}
