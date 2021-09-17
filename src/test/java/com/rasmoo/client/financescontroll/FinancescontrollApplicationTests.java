package com.rasmoo.client.financescontroll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class FinancescontrollApplicationTests {
	
	@Autowired
	private PasswordEncoder pass;
	
	@Test
	void contextLoads() {
		
	}

}
