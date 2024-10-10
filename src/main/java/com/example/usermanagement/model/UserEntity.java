package com.example.usermanagement.model;

import java.util.Set;

import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.IdGeneratorType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Username is mandatory")
	@Size(min=3, max = 20, message = "Username must be between 3 and 20 characters")
	@Column(nullable = false, unique = true)
	private String username;
	
	@NotBlank(message = "Name is mandatory")
	@Column(nullable = false)
	private String name;
	
	@NotBlank(message = "Surname is mandatory")
	@Column(nullable = false)
	@NotBlank
	private String surname;
	
	@Email
	@NotBlank(message = "Email is mandatory")
	@Size(min=3, max = 100, message = "Email must be between 3 and 100 characters")
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank(message = "Password is mandatory")
	@Column(nullable = false)
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER,
			targetEntity = RoleEntity.class,
			cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles;

}
