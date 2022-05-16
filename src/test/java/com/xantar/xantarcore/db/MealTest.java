package com.xantar.xantarcore.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.xantar.xantarcore.utils.Base64Utils;

public class MealTest {

	private static final int VALUE_ID = 1;
	private static final String VALUE_NAME = "name";
	private static final String VALUE_DESCRIPTION = "description";
	private static final byte[] VALUE_IMAGE = "image".getBytes();

	/*
	 * Builder
	 * */
	@Test
	void build_returnsCorrectMeal() {
		final var meal = Meal.builder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withImageThumb(VALUE_IMAGE)
				.withSlots(new ArrayList<Slot>())
				.build();

		assertNotNull(meal, "Meal should not be null");
		assertEquals(VALUE_ID, meal.id, "Id should be the same");
		assertEquals(VALUE_NAME, meal.name, "Name should be the same");
		assertEquals(VALUE_DESCRIPTION, meal.description, "Description should be the same");
		assertNotNull(meal.slots, "Slots should not be null");
		assertTrue(meal.slots.isEmpty(), "Slots should be empty");
		assertEquals(VALUE_IMAGE, meal.imageThumb, "Image should be the same");
	}

	/*
	 * new Meal(EMeal)
	 * */
	@Test
	void entityToModel_nullArgument_throwsException() {
		assertThrows(NullPointerException.class, () -> new Meal(null));
	}

	@Test
	void entityToModel_mapEntityWithoutNullValues_returnsCorrectModel() {

		final var eMeal = EMeal.builder()
				.withId(1)
				.withName("name")
				.withDescription("decription")
				.withImageThumb("YmFzaWM=".getBytes())
				.withSlots(new HashSet<ESlot>())
				.build();

		eMeal.slots.add(new ESlot(1, "slot1"));
		eMeal.slots.add(new ESlot(2, "slot2"));

		final var meal = new Meal(eMeal);

		assertNotNull(meal, "Meal should not be null");
		assertEquals(eMeal.id, meal.id, "Id should be the same");
		assertEquals(eMeal.name, meal.name, "Name should be the same");
		assertEquals(eMeal.description, meal.description, "Description should be the same");
		assertNotNull(eMeal.slots, "Slots should not be null");
		assertEquals(eMeal.slots.size(), meal.slots.size(), "Slots size should be the same");
		assertEquals(eMeal.imageThumb, Base64Utils.encodeImage(meal.imageThumb), "Image should be the same");
	}

	@Test
	void entityToModel_mapEntityWithNullValues_returnsCorrectModel() {
		final var eMeal = EMeal.builder().build();

		final var meal = new Meal(eMeal);

		assertNotNull(meal, "Meal should not be null");
		assertNull(meal.id, "Id should be null");
		assertNull(meal.name, "Name should be null");
		assertNull(meal.description, "Description should be null");
		assertNotNull(meal.slots, "Slots should not be null");
		assertTrue(meal.slots.isEmpty(), "Slots should be empty");
		assertNull(meal.imageThumb, "Image should be null");
	}
}
