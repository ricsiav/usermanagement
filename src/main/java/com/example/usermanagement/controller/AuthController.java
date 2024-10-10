package com.example.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.requestDTO.LoginRequestDTO;
import com.example.usermanagement.requestDTO.RegisterRequestDTO;
import com.example.usermanagement.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
	private final AuthService authService;
	
	@Autowired
	private final AuthenticationManager authenticationManager;


	@PostMapping("/register")
	public String register(@RequestBody RegisterRequestDTO userRegister) {
			return 	authService.register(userRegister);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO userLogin) {
		return ResponseEntity.ok().build();
	}
}
