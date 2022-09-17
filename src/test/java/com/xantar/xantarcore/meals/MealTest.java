package com.xantar.xantarcore.meals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MealTest {
	private static final String EMPTY_NAME_FALLBACK = "New meal";

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"  ", "\t", "\n"})
	void instantiateMeal_blankOrNullName_returnFallbackName(String notValidName) {
		var meal = Meal.builder().withName(notValidName).build();
		assertEquals(EMPTY_NAME_FALLBACK, meal.name(), "Name should be fallback name");
	}

	/*
	 * Builder
	 * */
	@Test
	void build_returnsCorrectMeal() {
		var meal = Meal.builder()
				.withId(1)
				.withName("name")
				.withDescription("description")
				.withImageThumb("image".getBytes())
				.withSlots(new ArrayList<>())
				.build();

		assertNotNull(meal, "Meal should not be null");
		assertEquals(1, meal.id(), "Id should be the same");
		assertEquals("name", meal.name(), "Name should be the same");
		assertEquals("description", meal.description(), "Description should be the same");
		assertNotNull(meal.slots(), "Slots should not be null");
		assertTrue(meal.slots().isEmpty(), "Slots should be empty");
		assertArrayEquals("image".getBytes(), meal.imageThumb(), "Image should be the same");
	}

}
