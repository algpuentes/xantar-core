package com.xantar.xantarcore.meals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MealResponseJsonTest {

	private static final int VALUE_ID = 1;
	private static final String VALUE_NAME = "name";
	private static final String VALUE_DESCRIPTION = "description";
	private static final String VALUE_IMAGE = "image";

	/*
	 * constructor(Builder)
	 * */
	@Test
	void constructor_withBuilderArg_returnsCorrectMeal() {
		final var builder = new MealResponseJson.Builder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withImageThumb(VALUE_IMAGE)
				.withSlots(new ArrayList<SlotResponseJson>());
		final var jMeal = new MealResponseJson(builder);

		assertNotNull(jMeal, "Meal should not be null");
		assertEquals(VALUE_ID, jMeal.id, "Id should be the same");
		assertEquals(VALUE_NAME, jMeal.name, "Name should be the same");
		assertEquals(VALUE_DESCRIPTION, jMeal.description, "Description should be the same");
		assertNotNull(jMeal.slots, "Slots should not be null");
		assertTrue(jMeal.slots.isEmpty(), "Slots should be empty");
		assertEquals(VALUE_IMAGE, jMeal.imageThumb, "Image should be the same");
	}

	/*
	 * toString()
	 * */
	@Test
	void toString_shouldReturnCorrectString() {
		final SlotResponseJson slot1 = new SlotResponseJson(1, "slot1");
		final SlotResponseJson slot2 = new SlotResponseJson(2, "slot2");
		final var resultingString = new MealResponseJson.Builder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withSlots(List.of(slot1, slot2))
				.build()
				.toString();

		final var expectedString = new StringBuilder()
				.append("{")
				.append("\"id\":" + VALUE_ID + ",")
				.append("\"name\":\"" + VALUE_NAME + "\",")
				.append("\"description\":\"" + VALUE_DESCRIPTION + "\",")
				.append("\"slots\":[" + slot1.toString() + "," + slot2.toString() +"]")
				.append("}")
				.toString();

		assertEquals(expectedString, resultingString);

	}

	/*
	 * Builder
	 * */
	@Test
	void build_returnsCorrectMeal() {
		final var builder = new MealResponseJson.Builder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withImageThumb(VALUE_IMAGE)
				.withSlots(new ArrayList<SlotResponseJson>());
		final var jMeal = builder.build();

		assertNotNull(jMeal, "Meal should not be null");
		assertEquals(VALUE_ID, jMeal.id, "Id should be the same");
		assertEquals(VALUE_NAME, jMeal.name, "Name should be the same");
		assertEquals(VALUE_DESCRIPTION, jMeal.description, "Description should be the same");
		assertNotNull(jMeal.slots, "Slots should not be null");
		assertTrue(jMeal.slots.isEmpty(), "Slots should be empty");
		assertEquals(VALUE_IMAGE, jMeal.imageThumb, "Image should be the same");
	}
}
