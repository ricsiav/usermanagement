package com.example.usermanagement.responseDTO;

import com.example.usermanagement.model.ERole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponseDTO {
	@Enumerated(EnumType.STRING)
	private ERole name;
}
