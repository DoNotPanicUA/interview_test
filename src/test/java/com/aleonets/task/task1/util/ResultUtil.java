package com.aleonets.task.task1.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.test.web.servlet.MvcResult;

@UtilityClass
public class ResultUtil {

	@SneakyThrows
	public <T> T parseJson(MvcResult mvcResult, TypeReference<T> resultClass, ObjectMapper objectMapper) {
		String content = mvcResult.getResponse().getContentAsString();
		return objectMapper.readValue(content, resultClass);
	}
}
