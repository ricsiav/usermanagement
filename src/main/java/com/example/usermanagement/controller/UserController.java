package com.example.usermanagement.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.model.UserEntity;
import com.example.usermanagement.requestDTO.UserEmailRequestDTO;
import com.example.usermanagement.requestDTO.UserRequestDTO;
import com.example.usermanagement.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/management/users")
@RequiredArgsConstructor
public class UserController {
	@Autowired
	private final UserService service;

	@GetMapping()
	public ResponseEntity<?> findUsers(){
		try {
			return ResponseEntity.ok(service.getAllUsers());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findUserById(@PathVariable(name = "id") Long id){
		try {
			return ResponseEntity.ok(service.getUser(id));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	@PostMapping("/newUser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createNewUser(
			@RequestBody UserRequestDTO user){
		try {
			return ResponseEntity.ok(service.saveUser(user));
		}catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	@PatchMapping("/updateEmail/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')") //This complements the annotation in SecurityConfig about role control
	public ResponseEntity<?> updateEmail(
			@PathVariable(name = "id") Long id,
			@RequestBody UserEmailRequestDTO email){
		try {
			service.updateEmail(id, email.getEmail());
			return ResponseEntity.ok("Email updated succefully");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}	
	
	@PostMapping("/updateUser")
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	public ResponseEntity<?> updateUser(
			@RequestBody UserRequestDTO user){
		try {
			return ResponseEntity.ok(service.updateUser(user));
		}catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUser(
			@PathVariable Long id){
		try {
			service.deleteUser(id);
			return ResponseEntity.ok("User delete sucessfully with id: "+id);
		}catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	


}
