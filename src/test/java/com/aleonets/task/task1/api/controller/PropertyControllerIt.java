package com.aleonets.task.task1.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.aleonets.task.task1.config.PostgresContainerConfiguration;
import com.aleonets.task.task1.util.TestDataUtil;
import lombok.SneakyThrows;
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
class PropertyControllerIt {

	@Autowired
	MockMvc mockMvc;

	@Test
	@SneakyThrows
	void getAll() {
		mockMvc.perform(get("/properties"))
				.andDo(print())
				.andExpect(status().isOk());
	}
}