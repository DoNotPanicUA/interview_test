package com.aleonets.task.task1.util;

import com.aleonets.task.task1.service.UserService;
import com.aleonets.task.task1.storage.entity.PropertyEntity;
import com.aleonets.task.task1.storage.entity.UserEntity;
import com.aleonets.task.task1.storage.entity.UserPropertyEntity;
import com.aleonets.task.task1.storage.repository.PropertyRepository;
import com.aleonets.task.task1.storage.repository.UserPropertyRepository;
import jakarta.transaction.Transactional;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class TestDataUtil {

	@Autowired UserService userService;
	@Autowired
	PropertyRepository propertyRepository;
	@Autowired
	UserPropertyRepository userPropertyRepository;

	@Transactional
	public void addUser(String email, String password) {
		userService.addUser(UserEntity.builder().email(email).password(password).isActive(true).build());
	}

	@Transactional
	public void addProperty(String id) {
		var newProperty = PropertyEntity.builder().id(id).price(new Random().nextDouble()).name("Generated"+id).build();
		propertyRepository.save(newProperty);
	}

	@Transactional
	public void addUserProperty(String propertyId, String userEmail) {
		var newUserProperty = UserPropertyEntity.builder().id(UUID.randomUUID()).property(propertyId).user(userEmail).build();
		userPropertyRepository.save(newUserProperty);
	}

	@Transactional
	public void copyUserProperties(String fromId, String toId) {
		userPropertyRepository.findAll().stream().filter(userPropertyEntity -> userPropertyEntity.getProperty().equals(fromId)).forEach(userPropertyEntity -> addUserProperty(toId, userPropertyEntity.getUser()));
	}

}
