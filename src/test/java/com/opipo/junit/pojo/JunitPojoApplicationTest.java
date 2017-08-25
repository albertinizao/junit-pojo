package com.opipo.junit.pojo;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("Application Context")
public class JunitPojoApplicationTest {

	@Test
	@DisplayName("Application context is correct")
	public void contextLoads() {

	}

}
