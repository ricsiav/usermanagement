package com.example.usermanagement.responseDTO;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
	Long id;
	String name;
	String surname;
	String email;
	String username;
	Set<RoleResponseDTO> roles;
}
