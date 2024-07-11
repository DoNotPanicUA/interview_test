package com.aleonets.task.task1.service;

import com.aleonets.task.task1.storage.entity.UserEntity;
import com.aleonets.task.task1.storage.repository.UserRepository;
import com.aleonets.task.task1.utils.EmailValidationUtils;
import com.aleonets.task.task1.utils.RegExpUtils;
import jakarta.transaction.Transactional;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

	UserRepository userRepository;

	@Transactional
	public void addUser(UserEntity newUser) {
		userRepository.save(validateEmail(newUser));
	}

	public boolean login(String email, String password) {
		EmailValidationUtils.validateEmail(email);
		var entity = userRepository.getByEmailAndAndIsActiveIsTrue(email);
		return (Objects.nonNull(entity) && Objects.equals(password, entity.getPassword()));
	}

	private UserEntity validateEmail(UserEntity userEntity) {
		EmailValidationUtils.validateEmail(userEntity.getEmail());
		return userEntity;
	}

}
