package com.aleonets.task.task1.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
import com.aleonets.task.task1.api.model.PropertyDetails;
import com.aleonets.task.task1.config.PostgresContainerConfiguration;
import com.aleonets.task.task1.util.ResultUtil;
import com.aleonets.task.task1.util.TestDataUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.flyway.clean-disabled=false")
@Import({PostgresContainerConfiguration.class, TestDataUtil.class})
@Slf4j
class PropertyDetailsControllerIT {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	TestDataUtil testDataUtil;
	@Autowired Flyway flyway;

	public static Stream<Arguments> valid() {
		return Stream.of(
				Arguments.of("p1", "p2", "p3"),
				Arguments.of("p2", "p1", "p3"),
				Arguments.of("p3", "p2", "p1")
		);
	}

	@BeforeEach
	void clearDatabase() {
		flyway.clean();
		flyway.migrate();
	}

	@ParameterizedTest
	@MethodSource("valid")
	@SneakyThrows
	void getPropertyDetails(String property, String mostSimilar, String leastSimilar) {
		var result = mockMvc.perform(get("/details/{propertyId}", property)
						.param("user","test1@engelvoelkers.com"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		PropertyDetails resultPropertyDetails = ResultUtil.parseJson(result, new TypeReference<>() {}, objectMapper);
		assertEquals(property, resultPropertyDetails.getId());

		var firstSim = resultPropertyDetails.getSimilarProperty().get(0);
		var lastSim = resultPropertyDetails.getSimilarProperty().get(resultPropertyDetails.getSimilarProperty().size()-1);

		assertEquals(mostSimilar, firstSim.getId());
		assertEquals(leastSimilar, lastSim.getId());
	}

	@Test
	@SneakyThrows
	void newPropertyWithoutUserRequests() {
		String newProp = "p4";
		testDataUtil.addProperty(newProp);

		var result = mockMvc.perform(get("/details/{propertyId}", newProp)
						.param("user","test1@engelvoelkers.com"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		PropertyDetails resultPropertyDetails = ResultUtil.parseJson(result, new TypeReference<>() {}, objectMapper);
		assertEquals(newProp, resultPropertyDetails.getId());
		assertEquals(0, resultPropertyDetails.getSimilarProperty().size());
	}

	@Test
	@SneakyThrows
	void newPropertyCopyPropRequests() {
		String newProp = "p5";
		String copyProp = "p1";
		testDataUtil.addProperty(newProp);
		testDataUtil.copyUserProperties(copyProp, newProp);

		var result = mockMvc.perform(get("/details/{propertyId}", newProp)
						.param("user","test1@engelvoelkers.com"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		PropertyDetails resultPropertyDetails = ResultUtil.parseJson(result, new TypeReference<>() {}, objectMapper);
		assertEquals(newProp, resultPropertyDetails.getId());
		assertEquals(copyProp, resultPropertyDetails.getSimilarProperty().get(0).getId());
	}
}