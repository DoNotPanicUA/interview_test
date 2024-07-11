package com.aleonets.task.task1.utils;

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RegExpUtils {

	public boolean patternMatches(String text, String regexPattern) {
		return Pattern.compile(regexPattern)
				.matcher(text)
				.matches();
	}
}
