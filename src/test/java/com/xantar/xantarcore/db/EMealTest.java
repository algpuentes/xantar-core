package com.xantar.xantarcore.db;

import com.xantar.xantarcore.meals.Meal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class EMealTest {

	private static final String EMPTY_NAME_FALLBACK = "New meal";
	private static final int VALUE_ID = 1;
	private static final String VALUE_NAME = "name";
	private static final String VALUE_DESCRIPTION = "description";
	private static final byte[] VALUE_IMAGE = "image".getBytes();

	/*
	 * addSlot(ESlot)
	 * */
	@Test
	void addSlot_shouldAddSlotToSetAndMealToSlot() {
		var eSlot = new ESlot();
		eSlot.id = VALUE_ID;
		eSlot.name = VALUE_NAME;

		var eMeal = new EMeal();

		eMeal.addSlot(eSlot);

		assertNotNull(eMeal.slots);
		assertEquals(1, eMeal.slots.size());
		assertTrue(eMeal.slots.contains(eSlot));
	}

	/*
	 * removeSlot(ESlot)
	 * */
	@Test
	void removeSlot_shouldRemoveSlotFromSetAndMealFromSlot() {
		var eSlot = new ESlot();
		eSlot.id = VALUE_ID;
		eSlot.name = VALUE_NAME;

		var eMeal = new EMeal();
		eMeal.id = VALUE_ID;
		eMeal.name = VALUE_NAME;

		eMeal.addSlot(eSlot);
		eMeal.removeSlot(eSlot);

		assertNotNull(eMeal.slots);
		assertTrue(eMeal.slots.isEmpty());

	}

	/**
	 * Constructors
	 * */
	@Test
	void constructorWithMealParameter_returnsCorrespondingMeal() {
		var builder = Meal.builder()
				.withId(VALUE_ID)
				.withName(VALUE_NAME)
				.withDescription(VALUE_DESCRIPTION)
				.withImageThumb(VALUE_IMAGE)
				.withSlots(new ArrayList<>());
		var meal = builder.build();

		var eMeal = new EMeal(meal);

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
	 * toMeal()
	 * */
	@Test
	void entityToModel_mapEntityWithoutNullValues_returnsCorrectModel() {

		var eMeal = new EMeal();
		eMeal.id = 1;
		eMeal.name = "name";
		eMeal.description = "description";
		eMeal.imageThumb = "YmFzaWM=".getBytes();
		eMeal.slots = new HashSet<>();

		eMeal.slots.add(new ESlot(1, "slot1"));
		eMeal.slots.add(new ESlot(2, "slot2"));

		var meal = eMeal.toMeal();

		assertNotNull(meal, "Meal should not be null");
		assertEquals(eMeal.id, meal.id(), "Id should be the same");
		assertEquals(eMeal.name, meal.name(), "Name should be the same");
		assertEquals(eMeal.description, meal.description(), "Description should be the same");
		assertNotNull(eMeal.slots, "Slots should not be null");
		assertEquals(eMeal.slots.size(), meal.slots().size(), "Slots size should be the same");
		assertEquals(eMeal.imageThumb, meal.imageThumb(), "Image should be the same");
	}

	@Test
	void entityToModel_mapEntityWithNullValuesButName_returnsCorrectModel() {
		var eMeal = new EMeal();

		var meal = eMeal.toMeal();

		assertNotNull(meal, "Meal should not be null");
		assertNull(meal.id(), "Id should be null");
		assertEquals(EMPTY_NAME_FALLBACK, meal.name(), "Name should be fallback name");
		assertNull(meal.description(), "Description should be null");
		assertNotNull(meal.slots(), "Slots should not be null");
		assertTrue(meal.slots().isEmpty(), "Slots should be empty");
		assertNull(meal.imageThumb(), "Image should be null");
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"  ", "\t", "\n"})
	void entityToModel_mapEntityWithNullOrBlankName_returnFallbackName(String notValidName) {
		var eMeal = new EMeal();
		eMeal.name = notValidName;

		var meal = eMeal.toMeal();
		assertEquals(EMPTY_NAME_FALLBACK, meal.name(), "Name should be fallback name");
	}
}
