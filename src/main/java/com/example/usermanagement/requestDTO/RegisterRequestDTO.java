package com.example.usermanagement.requestDTO;

import java.util.Set;

import javax.management.relation.Role;

import com.example.usermanagement.model.ERole;

import lombok.Data;

@Data
public class RegisterRequestDTO {
	String name;
	String surname;
	String email;
	String username;
	String password;
	Set<ERole> role;
}
