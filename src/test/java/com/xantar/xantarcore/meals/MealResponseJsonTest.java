package com.xantar.xantarcore.meals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class MealResponseJsonTest {

	private static final int VALUE_ID = 1;
	private static final String VALUE_NAME = "name";
	private static final String VALUE_DESCRIPTION = "description";
	private static final String VALUE_IMAGE = "image";

	/*
	 * Builder
	 * */
	@Test
	void build_returnsCorrectMeal() {
		var jMeal = MealResponseJson.builder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withImageThumb(VALUE_IMAGE)
				.withSlots(new ArrayList<>())
				.build();

		assertNotNull(jMeal, "Meal should not be null");
		assertEquals(VALUE_ID, jMeal.id(), "Id should be the same");
		assertEquals(VALUE_NAME, jMeal.name(), "Name should be the same");
		assertEquals(VALUE_DESCRIPTION, jMeal.description(), "Description should be the same");
		assertNotNull(jMeal.slots(), "Slots should not be null");
		assertTrue(jMeal.slots().isEmpty(), "Slots should be empty");
		assertEquals(VALUE_IMAGE, jMeal.imageThumb(), "Image should be the same");
	}
}
