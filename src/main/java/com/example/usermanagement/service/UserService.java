package com.example.usermanagement.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usermanagement.model.ERole;
import com.example.usermanagement.model.RoleEntity;
import com.example.usermanagement.model.UserEntity;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.requestDTO.UserRequestDTO;
import com.example.usermanagement.responseDTO.RoleResponseDTO;
import com.example.usermanagement.responseDTO.UserResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private final UserRepository repository;
	
	@Autowired
    private PasswordGeneratorService passwordGeneratorService;
	
	@Autowired
	private final PasswordEncoder encoder;
	
	public List<UserResponseDTO> getAllUsers(){
		List<UserEntity> users = repository.findAll();
		List<UserResponseDTO> usersResponse = users.stream()
												.map(this::mapToDTO)
												.collect(Collectors.toList());
		return usersResponse;
	}
	
	public UserResponseDTO getUser(Long id) throws Exception {
		UserEntity user =  getUserById(id);
		UserResponseDTO usersResponse = mapToDTO(user);
		return usersResponse;
	}
	
	private UserEntity getUserById(Long id) throws Exception{
		UserEntity user = repository.findById(id).orElseThrow(
				() -> new Exception("User not found with id: "+id));
		return user;
	}
	
	public UserResponseDTO saveUser(UserRequestDTO user) {
		Set<RoleEntity> roles = new HashSet<>();
        for (ERole role : user.getRole()) {
            RoleEntity roleEntity = RoleEntity.builder()
            									.name(role)
            									.build();
            roles.add(roleEntity);}
		String password = passwordGeneratorService.generatePassword();
		UserEntity userNew = UserEntity.builder()
										.email(user.getEmail())
										.username(user.getUsername())
										.password(encoder.encode(password))
										.roles(roles)
										.build();
		UserEntity savedUser = repository.save(userNew);
		UserResponseDTO usersResponse = mapToDTO(savedUser);
		return usersResponse;
	}
	
	public UserResponseDTO updateUser(UserRequestDTO user)  throws Exception{
		UserEntity userUpdate = getUserById(user.getId());
		Set<RoleEntity> roles = new HashSet<>();
        for (ERole role : user.getRole()) {
            RoleEntity roleEntity = RoleEntity.builder()
            									.name(role)
            									.build();
            roles.add(roleEntity);}
		userUpdate.setEmail(user.getEmail());
		userUpdate.setUsername(user.getUsername());
		userUpdate.setRoles(roles);
		UserEntity updatedUser = repository.save(userUpdate);
		UserResponseDTO usersResponse = mapToDTO(updatedUser);
		return usersResponse;
	}
	
	public void updateEmail(Long id,String email) throws Exception {
		UserEntity userUpdate = getUserById(id);
		if(userUpdate == null) {
			throw new Exception("User not found");
		}
		userUpdate.setEmail(email);
		repository.save(userUpdate);
	}
	
	public void deleteUser(Long id) throws Exception {
		UserEntity user = getUserById(id);
		repository.deleteById(id);
	}
	
	private UserResponseDTO mapToDTO(UserEntity user) {
		Set<RoleResponseDTO> rolesDTO = new HashSet<>();
        for (RoleEntity role : user.getRoles()) {
            RoleResponseDTO roleDTO = RoleResponseDTO.builder()
            									.name(role.getName())
            									.build();
            rolesDTO.add(roleDTO);
            }
        UserResponseDTO usersResponse = UserResponseDTO.builder()
        									.id(user.getId())
        									.name(user.getName())
        									.surname(user.getSurname())
        									.email(user.getEmail())
        									.username(user.getUsername())
        									.roles(rolesDTO)
        									.build();

        return usersResponse;
    }
	
	
}
