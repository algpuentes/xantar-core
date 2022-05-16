package com.xantar.xantarcore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class XantarCoreApplicationTests {

	// Test class added to cover main() invocation not covered by application tests.
	@Test
	void contextLoads() {
		XantarCoreApplication.main(new String[] {});
	}

}
