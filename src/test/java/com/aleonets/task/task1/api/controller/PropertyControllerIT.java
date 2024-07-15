package com.aleonets.task.task1.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.aleonets.task.task1.api.model.Property;
import com.aleonets.task.task1.config.PostgresContainerConfiguration;
import com.aleonets.task.task1.storage.mapping.PropertyMapping;
import com.aleonets.task.task1.storage.repository.PropertyRepository;
import com.aleonets.task.task1.util.ResultUtil;
import com.aleonets.task.task1.util.TestDataUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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
@Slf4j
class PropertyControllerIT {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	PropertyRepository propertyRepository;
	@Autowired
	PropertyMapping propertyMapping;

	@Test
	@SneakyThrows
	void getAll() {
		var res = mockMvc.perform(get("/properties"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		List<Property> resultPropertyList = ResultUtil.parseJson(res, new TypeReference<>() {
		}, objectMapper);
		List<Property> expectedPropertyList = propertyMapping.toModel(propertyRepository.findAll());

		assertEquals(expectedPropertyList.size(), resultPropertyList.size());
		assertEquals(expectedPropertyList.stream().map(Property::getId).toList(),
				resultPropertyList.stream().map(Property::getId).toList());
		assertEquals(expectedPropertyList.stream().map(Property::getPrice).toList(),
				resultPropertyList.stream().map(Property::getPrice).toList());
	}
}