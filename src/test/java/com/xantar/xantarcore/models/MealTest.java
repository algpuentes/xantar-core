package com.xantar.xantarcore.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.xantar.xantarcore.models.Meal.MealBuilder;

public class MealTest {

	private static final int VALUE_ID = 1;
	private static final String VALUE_NAME = "name";
	private static final String VALUE_DESCRIPTION = "description";
	private static final byte[] VALUE_IMAGE = "image".getBytes();

	/*
	 * constructor(Builder)
	 * */
	@Test
	void constructor_withBuilderArg_returnsCorrectMeal() {
		final var builder = new MealBuilder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withImageThumb(VALUE_IMAGE)
				.withSlots(new ArrayList<Slot>());
		final var meal = new Meal(builder);

		assertNotNull(meal, "Meal should not be null");
		assertEquals(VALUE_ID, meal.id, "Id should be the same");
		assertEquals(VALUE_NAME, meal.name, "Name should be the same");
		assertEquals(VALUE_DESCRIPTION, meal.description, "Description should be the same");
		assertNotNull(meal.slots, "Slots should not be null");
		assertTrue(meal.slots.isEmpty(), "Slots should be empty");
		assertEquals(VALUE_IMAGE, meal.imageThumb, "Image should be the same");
	}

	/*
	 * hashCode() && equals(Object)
	 * */
	@Test
	void hashCode_withSameAttributes_equal() {
		final var eMeal1 = new MealBuilder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withImageThumb(VALUE_IMAGE)
				.withSlots(new ArrayList<Slot>())
				.build();

		final var eMeal2 = new MealBuilder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withImageThumb(VALUE_IMAGE)
				.withSlots(new ArrayList<Slot>())
				.build();

		assertTrue(eMeal1.equals(eMeal2) && eMeal2.equals(eMeal1));
		assertEquals(eMeal1.hashCode(),  eMeal2.hashCode());
	}

	@Test
	void hashCode_withDifferentAttributes_notEqual() {
		final var eMeal1 = new MealBuilder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withImageThumb(VALUE_IMAGE)
				.withSlots(new ArrayList<Slot>())
				.build();

		final var eMeal2 = new MealBuilder()
				.withId(VALUE_ID)
				.withName("diff_name")
				.withDescription("diff_desc")
				.withImageThumb("dif_image".getBytes())
				.withSlots(new ArrayList<Slot>())
				.build();

		assertNotEquals(eMeal1, eMeal2);
		assertNotEquals(eMeal1.hashCode(),  eMeal2.hashCode());
	}

	/*
	 * toString()
	 * */
	@Test
	void toString_shouldReturnCorrectString() {
		final Slot slot1 = new Slot(1, "slot1");
		final Slot slot2 = new Slot(2, "slot2");
		final var resultingString = new MealBuilder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withSlots(List.of(slot1, slot2))
				.build()
				.toString();

		final var expectedString = new StringBuilder()
				.append("{")
				.append("id=" + VALUE_ID + ", ")
				.append("name=" + VALUE_NAME + ", ")
				.append("description=" + VALUE_DESCRIPTION + ", ")
				.append("slots=[" + slot1.toString() + ", " + slot2.toString() +"]")
				.append("}")
				.toString();

		assertEquals(expectedString, resultingString);

	}


	/*
	 * Builder
	 * */
	@Test
	void build_returnsCorrectMeal() {
		final var builder = new MealBuilder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withImageThumb(VALUE_IMAGE)
				.withSlots(new ArrayList<Slot>());
		final var meal = builder.build();

		assertNotNull(meal, "Meal should not be null");
		assertEquals(VALUE_ID, meal.id, "Id should be the same");
		assertEquals(VALUE_NAME, meal.name, "Name should be the same");
		assertEquals(VALUE_DESCRIPTION, meal.description, "Description should be the same");
		assertNotNull(meal.slots, "Slots should not be null");
		assertTrue(meal.slots.isEmpty(), "Slots should be empty");
		assertEquals(VALUE_IMAGE, meal.imageThumb, "Image should be the same");
	}
}
