package com.aleonets.task.task1.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.aleonets.task.task1.config.PostgresContainerConfiguration;
import com.aleonets.task.task1.util.TestDataUtil;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({PostgresContainerConfiguration.class, TestDataUtil.class})
class LoginControllerIT {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	TestDataUtil testDataUtil;

	public static Stream<Arguments> validLogins() {
		return Stream.of(
				Arguments.of("a@a.com", "123"),
				Arguments.of("b@a.com", "321")
		);
	}

	public static Stream<Arguments> invalidLogins() {
		return Stream.of(
				Arguments.of("a@a.com", "321"),
				Arguments.of("bqqq@a.com", "321")
		);
	}

	public static Stream<Arguments> invalidEmails() {
		return Stream.of(
				Arguments.of("a a@a.com"),
				Arguments.of(" b@a.com"),
				Arguments.of(" b@@a.com"),
				Arguments.of("a@test.com"),
				Arguments.of("a@t.com@")
		);
	}

	@ParameterizedTest
	@MethodSource("validLogins")
	@SneakyThrows
	void login(String email, String pass) {
		mockMvc.perform(get("/login").param("email", email).param("password", pass))
				.andDo(print())
				.andExpect(status().isOk()).andExpect(content().string("User login successfully!"));
	}

	@ParameterizedTest
	@MethodSource("invalidEmails")
	@SneakyThrows
	void loginTestCom(String email) {
		String pass = "123";

		var result = mockMvc.perform(get("/login").param("email", email).param("password", pass))
				.andDo(print())
				.andExpect(status().isUnprocessableEntity())
				.andReturn();

		assertEquals(
				"Invalid email",
				result.getResponse().getErrorMessage());
	}

	@ParameterizedTest
	@MethodSource("invalidLogins")
	@SneakyThrows
	void invalidLogin(String email, String pass) {
		mockMvc.perform(get("/login").param("email", email).param("password", pass))
				.andDo(print())
				.andExpect(status().isForbidden())
				.andExpect(content().string("Invalid user credentials!"));
	}
}