package com.aleonets.task.task1.api.controller;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.aleonets.task.task1.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LoginController {

	UserService userService;

	@GetMapping
	public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
		return (userService.login(email, password) ?
				ResponseEntity.ok("User login successfully!") :
				ResponseEntity.status(FORBIDDEN).body("Invalid user credentials!"));
	}
}
