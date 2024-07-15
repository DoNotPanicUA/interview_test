package com.aleonets.task.task1.utils;

import static org.junit.jupiter.api.Assertions.*;

import com.aleonets.task.task1.api.exception.InvalidEmailException;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EmailValidationUtilsTest {

	public static Stream<Arguments> invalidEmails() {
		return Stream.of(
				Arguments.of("a a@a.com"),
				Arguments.of(" b@a.com"),
				Arguments.of(" b@@a.com"),
				Arguments.of("a@test.com"),
				Arguments.of("a@Test.com"),
				Arguments.of("a@t.com@")
		);
	}

	@ParameterizedTest
	@MethodSource("invalidEmails")
	@SneakyThrows
	void loginTestCom(String email) {
		assertThrows(InvalidEmailException.class, () -> EmailValidationUtils.validateEmail(email));
	}
}