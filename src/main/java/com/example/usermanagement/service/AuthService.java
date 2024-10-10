package com.example.usermanagement.service;

import java.util.HashSet;
import java.util.Set;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usermanagement.jwt.JwtService;
import com.example.usermanagement.model.ERole;
import com.example.usermanagement.model.RoleEntity;
import com.example.usermanagement.model.UserEntity;
import com.example.usermanagement.repository.RoleRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.requestDTO.RegisterRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Autowired
	private final UserRepository repository;
	
	@Autowired
	private final JwtService jwtService;
	
	@Autowired
	private final PasswordEncoder encoder;
	
	public String register(RegisterRequestDTO userRegister) {
		System.out.print("Hola");
		Set<RoleEntity> roles = new HashSet<>();
        for (ERole role : userRegister.getRole()) {
            RoleEntity roleEntity = RoleEntity.builder()
            									.name(role)
            									.build();
            roles.add(roleEntity);}
		UserEntity user = UserEntity.builder()
									.name(userRegister.getName())
									.surname(userRegister.getSurname())
									.email(userRegister.getEmail())
									.username(userRegister.getUsername())
									.password(encoder.encode(userRegister.getPassword()))
									.roles(roles)
									.build();
		String jwt = jwtService.generateToken(user.getUsername());
		repository.save(user);
		return jwt;	
	}
}
