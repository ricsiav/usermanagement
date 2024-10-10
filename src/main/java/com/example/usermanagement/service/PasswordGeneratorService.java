package com.example.usermanagement.service;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
public class PasswordGeneratorService {

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
	private static final int PASSWORD_LENGTH = 12;

	private final SecureRandom secureRandom = new SecureRandom();

	public String generatePassword() {
		StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int randomIndex = secureRandom.nextInt(CHARACTERS.length());
			password.append(CHARACTERS.charAt(randomIndex));
		}
		return password.toString();
	}
}
