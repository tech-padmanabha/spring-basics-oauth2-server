package io.pn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UsersController {
	
	@GetMapping("/api/check")
	public String checkUser() {
		
		return "User Checked...!";
	}
}
