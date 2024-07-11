package com.aleonets.task.task1;

import com.aleonets.task.task1.config.PostgresContainerConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(PostgresContainerConfiguration.class)
@SpringBootTest
class TaskApplicationTests {

	@Test
	void contextLoads() {
	}

}
