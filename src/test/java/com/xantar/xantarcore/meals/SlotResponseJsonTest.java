package com.xantar.xantarcore.meals;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SlotResponseJsonTest {

	private static final int VALUE_ID = 1;
	private static final String VALUE_NAME = "name";

	/*
	 * toString()
	 * */
	@Test
	void toString_shouldReturnExpectedString() {

		final var slot = new SlotResponseJson(VALUE_ID, VALUE_NAME);
		final var resultingString = slot.toString();

		final var expectedString = new StringBuilder()
				.append("{")
				.append("\"id\":" + VALUE_ID + ",")
				.append("\"name\":\"" + VALUE_NAME + "\"}")
				.toString();

		assertEquals(expectedString, resultingString);

	}

}
