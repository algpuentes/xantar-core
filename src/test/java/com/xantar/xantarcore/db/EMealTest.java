package com.xantar.xantarcore.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class EMealTest {

	private static final int VALUE_ID = 1;
	private static final String VALUE_NAME = "name";
	private static final String VALUE_DESCRIPTION = "description";
	private static final byte[] VALUE_IMAGE = "image".getBytes();

	/*
	 * addSlot(ESlot)
	 * */
	@Test
	void addSlot_shouldAddSlotToSetAndMealToSlot() {
		final var eSlot = new ESlot();
		eSlot.id = VALUE_ID;
		eSlot.name = VALUE_NAME;

		final var eMeal = new EMeal();

		eMeal.addSlot(eSlot);

		assertNotNull(eMeal.slots);
		assertTrue(eMeal.slots.size() == 1);
		assertTrue(eMeal.slots.contains(eSlot));

		assertTrue(eSlot.meals.size() == 1);
		assertTrue(eSlot.meals.contains(eMeal));
	}

	/*
	 * removeSlot(ESlot)
	 * */
	@Test
	void removeSlot_shouldRemoveSlotFromSetAndMealFromSlot() {
		final var eSlot = new ESlot();
		eSlot.id = VALUE_ID;
		eSlot.name = VALUE_NAME;

		final var eMeal = EMeal.builder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.build();

		eMeal.addSlot(eSlot);
		eMeal.removeSlot(eSlot);

		assertNotNull(eMeal.slots);
		assertTrue(eMeal.slots.isEmpty());
		assertFalse(eMeal.slots.contains(eSlot));

		assertTrue(eSlot.meals.isEmpty());
		assertFalse(eSlot.meals.contains(eMeal));
	}

	/**
	 * Constructors
	 * */
	@Test
	void constructorWithMealParameter_returnsCorrespondingMeal() {
		final var builder = Meal.builder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withImageThumb(VALUE_IMAGE)
				.withSlots(new ArrayList<Slot>());
		final var meal = builder.build();

		final var eMeal = new EMeal(meal);

		assertNotNull(eMeal, "Meal should not be null");
		assertEquals(VALUE_ID, eMeal.id, "Id should be the same");
		assertEquals(VALUE_NAME, eMeal.name, "Name should be the same");
		assertEquals(VALUE_DESCRIPTION, eMeal.description, "Description should be the same");
		assertNotNull(eMeal.slots, "Slots should not be null");
		assertTrue(eMeal.slots.isEmpty(), "Slots should be empty");
		assertEquals(VALUE_IMAGE, eMeal.imageThumb, "Image should be the same");
	}

	@Test
	void constructorWithNullParameter_throwsNullPointerException() {
		assertThrows(NullPointerException.class, () -> new EMeal(null));
	}

	/*
	 * Builder
	 * */
	@Test
	void build_returnsCorrectEMeal() {
		final var builder = EMeal.builder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withImageThumb(VALUE_IMAGE)
				.withSlots(new HashSet<ESlot>());
		final var eMeal = builder.build();

		assertNotNull(eMeal, "Meal should not be null");
		assertEquals(VALUE_ID, eMeal.id, "Id should be the same");
		assertEquals(VALUE_NAME, eMeal.name, "Name should be the same");
		assertEquals(VALUE_DESCRIPTION, eMeal.description, "Description should be the same");
		assertNotNull(eMeal.slots, "Slots should not be null");
		assertTrue(eMeal.slots.isEmpty(), "Slots should be empty");
		assertEquals(VALUE_IMAGE, eMeal.imageThumb, "Image should be the same");
	}
}
