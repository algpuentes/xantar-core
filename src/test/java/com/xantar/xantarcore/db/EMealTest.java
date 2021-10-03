package com.xantar.xantarcore.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.xantar.xantarcore.db.EMeal.EMealBuilder;

public class EMealTest {

	private static final int VALUE_ID = 1;
	private static final String VALUE_NAME = "name";
	private static final String VALUE_DESCRIPTION = "description";
	private static final byte[] VALUE_IMAGE = "image".getBytes();

	/*
	 * constructor(Builder)
	 * */
	@Test
	void constructor_withBuilderArg_returnsCorrectEMeal() {
		final var builder = new EMealBuilder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withImageThumb(VALUE_IMAGE)
				.withSlots(new HashSet<ESlot>());
		final var eMeal = new EMeal(builder);

		assertNotNull(eMeal, "Meal should not be null");
		assertEquals(VALUE_ID, eMeal.id, "Id should be the same");
		assertEquals(VALUE_NAME, eMeal.name, "Name should be the same");
		assertEquals(VALUE_DESCRIPTION, eMeal.description, "Description should be the same");
		assertNotNull(eMeal.slots, "Slots should not be null");
		assertTrue(eMeal.slots.isEmpty(), "Slots should be empty");
		assertEquals(VALUE_IMAGE, eMeal.imageThumb, "Image should be the same");
	}

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

		final var eMeal = new EMealBuilder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME).build();

		eMeal.addSlot(eSlot);
		eMeal.removeSlot(eSlot);

		assertNotNull(eMeal.slots);
		assertTrue(eMeal.slots.isEmpty());
		assertFalse(eMeal.slots.contains(eSlot));

		assertTrue(eSlot.meals.isEmpty());
		assertFalse(eSlot.meals.contains(eMeal));
	}

	/*
	 * hashCode() && equals(Object)
	 * */
	@Test
	void hashCode_withSameAttributes_equal() {
		final var eMeal1 = new EMeal();
		eMeal1.id = VALUE_ID;
		eMeal1.name = VALUE_NAME;
		eMeal1.description = VALUE_DESCRIPTION;
		eMeal1.imageThumb = VALUE_IMAGE;

		final var eMeal2 = new EMeal();
		eMeal2.id = VALUE_ID;
		eMeal2.name = VALUE_NAME;
		eMeal2.description = VALUE_DESCRIPTION;
		eMeal2.imageThumb = VALUE_IMAGE;

		assertTrue(eMeal1.equals(eMeal2) && eMeal2.equals(eMeal1));
		assertEquals(eMeal1.hashCode(),  eMeal2.hashCode());
	}

	@Test
	void hashCode_withDifferentAttributes_notEqual() {
		final var eMeal1 = new EMeal();
		eMeal1.id = VALUE_ID;
		eMeal1.name = VALUE_NAME;
		eMeal1.description = VALUE_DESCRIPTION;
		eMeal1.imageThumb = VALUE_IMAGE;

		final var eMeal2 = new EMeal();
		eMeal2.id = VALUE_ID;
		eMeal2.name = "diff_name";
		eMeal2.description = "diff_desc";
		eMeal2.imageThumb = "dif_image".getBytes();

		assertNotEquals(eMeal1, eMeal2);
		assertNotEquals(eMeal1.hashCode(),  eMeal2.hashCode());
	}

	/*
	 * Builder
	 * */
	@Test
	void build_returnsCorrectEMeal() {
		final var builder = new EMealBuilder()
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
