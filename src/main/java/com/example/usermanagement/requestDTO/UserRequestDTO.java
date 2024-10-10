package com.example.usermanagement.requestDTO;

import java.util.Set;

import com.example.usermanagement.model.ERole;

import lombok.Data;

@Data
public class UserRequestDTO {
	Long id;
	String name;
	String surname;
	String email;
	String username;
	Set<ERole> role;
}
