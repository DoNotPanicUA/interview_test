package com.aleonets.task.task1.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.aleonets.task.task1.config.PostgresContainerConfiguration;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
@Import({PostgresContainerConfiguration.class})
@Slf4j
class PropertyDetailsControllerIt {

	@Autowired
	MockMvc mockMvc;

	public static Stream<Arguments> valid() {
		return Stream.of(
				Arguments.of("p1"),
				Arguments.of("p2"),
				Arguments.of("p3")
		);
	}

	@ParameterizedTest
	@MethodSource("valid")
	@SneakyThrows
	void getAll(String property) {
		mockMvc.perform(get("/details/{propertyId}", property)
						.param("user","test1@engelvoelkers.com"))
				.andDo(print())
				.andExpect(status().isOk());
		log.info("");
	}
}