package com.aleonets.task.task1.utils;

import com.aleonets.task.task1.api.exception.InvalidEmailException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@UtilityClass
@Slf4j
public class EmailValidationUtils {

	private final String emailPattern = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

	public void validateEmail(String email) {
		if (StringUtils.hasText(email) && RegExpUtils.patternMatches(email, emailPattern) && !RegExpUtils.patternMatches(email.toLowerCase(), ".*@test.com")) {
			log.info("{} is real email!", email);
		} else {
			throw new InvalidEmailException(email);
		}
	}
}
