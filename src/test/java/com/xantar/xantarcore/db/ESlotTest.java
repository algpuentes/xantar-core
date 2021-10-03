package com.xantar.xantarcore.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ESlotTest {

	private static final int VALUE_ID = 1;
	private static final String VALUE_NAME = "name";


	/*
	 * addSlot(ESlot)
	 * */
	@Test
	void addSlot_shouldAddSlotToSetAndMealToSlot() {
		final var eSlot = new ESlot();

		final var eMeal = new EMeal();
		eMeal.id = VALUE_ID;
		eMeal.name = VALUE_NAME;

		eSlot.addMeal(eMeal);

		assertNotNull(eSlot.meals);
		assertTrue(eSlot.meals.size() == 1);
		assertTrue(eSlot.meals.contains(eMeal));

		assertTrue(eMeal.slots.size() == 1);
		assertTrue(eMeal.slots.contains(eSlot));
	}


	/*
	 * removeSlot(ESlot)
	 * */
	@Test
	void removeSlot_shouldRemoveSlotFromSetAndMealFromSlot() {
		final var eSlot = new ESlot();

		final var eMeal = new EMeal();
		eMeal.id = VALUE_ID;
		eMeal.name = VALUE_NAME;

		eSlot.addMeal(eMeal);
		eSlot.removeMeal(eMeal);

		assertNotNull(eSlot.meals);
		assertTrue(eSlot.meals.isEmpty());
		assertFalse(eSlot.meals.contains(eMeal));

		assertTrue(eMeal.slots.isEmpty());
		assertFalse(eMeal.slots.contains(eSlot));
	}

	/*
	 * hashCode() && equals(Object)
	 * */
	@Test
	void hashCode_withSameAttributes_equal() {
		final var eSlot1 = new ESlot();
		eSlot1.id = VALUE_ID;
		eSlot1.name = VALUE_NAME;

		final var eSlot2 = new ESlot();
		eSlot2.id = VALUE_ID;
		eSlot2.name = VALUE_NAME;

		assertTrue(eSlot1.equals(eSlot2) && eSlot2.equals(eSlot1));
		assertEquals(eSlot1.hashCode(),  eSlot2.hashCode());
	}

	@Test
	void hashCode_withDifferentAttributes_notEqual() {
		final var eSlot1 = new ESlot();
		eSlot1.id = VALUE_ID;
		eSlot1.name = VALUE_NAME;

		final var eSlot2 = new ESlot();
		eSlot2.id = VALUE_ID;
		eSlot2.name = "diff_name";

		assertNotEquals(eSlot1, eSlot2);
		assertNotEquals(eSlot1.hashCode(),  eSlot2.hashCode());
	}

}
