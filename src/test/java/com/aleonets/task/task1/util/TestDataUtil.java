package com.aleonets.task.task1.util;

import com.aleonets.task.task1.service.UserService;
import com.aleonets.task.task1.storage.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class TestDataUtil {

	@Autowired UserService userService;

	public void addUser(String email, String password) {
		userService.addUser(UserEntity.builder().email(email).password(password).isActive(true).build());
	}

}
