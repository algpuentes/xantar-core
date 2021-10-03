package com.xantar.xantarcore.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SlotTest {

	private static final int VALUE_ID = 1;
	private static final String VALUE_NAME = "name";


	/*
	 * hashCode() && equals(Object)
	 * */
	@Test
	void hashCode_withSameAttributes_equal() {
		final var slot1 = new Slot(VALUE_ID, VALUE_NAME);

		final var slot2 = new Slot(VALUE_ID, VALUE_NAME);

		assertTrue(slot1.equals(slot2) && slot2.equals(slot1));
		assertEquals(slot1.hashCode(),  slot2.hashCode());
	}

	@Test
	void hashCode_withDifferentAttributes_notEqual() {
		final var slot1 = new Slot(VALUE_ID, VALUE_NAME);

		final var slot2 = new Slot(VALUE_ID, "diff_name");

		assertNotEquals(slot1, slot2);
		assertNotEquals(slot1.hashCode(),  slot2.hashCode());
	}

	/*
	 * toString()
	 * */
	@Test
	void toString_shouldReturnExpectedString() {

		final var slot = new Slot(VALUE_ID, VALUE_NAME);
		final var resultingString = slot.toString();

		final var expectedString = new StringBuilder()
				.append("{")
				.append("id=" + VALUE_ID + ", ")
				.append("name=" + VALUE_NAME + "}")
				.toString();

		assertEquals(expectedString, resultingString);

	}
}
