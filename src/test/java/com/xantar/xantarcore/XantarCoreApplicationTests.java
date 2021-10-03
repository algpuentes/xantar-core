package com.xantar.xantarcore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XantarCoreApplicationTests {

	// Test class added to cover main() invocation not covered by application tests.
	@Test
	void contextLoads() {
		XantarCoreApplication.main(new String[] {});
	}

}
