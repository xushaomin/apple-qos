package com.appleframework.qos.server.agent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring-*.xml" })
public class StarterAgentTest {
	
	@Test
	public void testTruckerPositionSearch() {
		try {
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
