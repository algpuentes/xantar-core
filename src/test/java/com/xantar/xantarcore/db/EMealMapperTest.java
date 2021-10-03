package com.xantar.xantarcore.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.xantar.xantarcore.models.Meal;
import com.xantar.xantarcore.models.Slot;

public class EMealMapperTest {

	/*
	 * toModel(EMeal)
	 * */
	@Test
	void entityToModel_mapNullEntity_returnsNullModel() {
		final var meal = EMealMapper.toModel(null);

		assertNull(meal, "Mapped meal model should be null.");
	}

	@Test
	void entityToModel_mapEntityWithoutNullValues_returnsCorrectModel() {
		final var eMeal = new EMeal();
		eMeal.id = 1;
		eMeal.name = "name";
		eMeal.description = "decription";
		eMeal.imageThumb = "image".getBytes();

		final var eSlot1 = new ESlot();
		final var eSlot2 = new ESlot();
		final HashSet<ESlot> eSlotSet = new HashSet<ESlot>();
		eSlotSet.add(eSlot1);
		eSlotSet.add(eSlot2);

		eMeal.slots = eSlotSet;

		final var meal = EMealMapper.toModel(eMeal);

		assertNotNull(meal, "Meal should not be null");
		assertEquals(meal.id, eMeal.id, "Id should be the same");
		assertEquals(meal.name, eMeal.name, "Name should be the same");
		assertEquals(meal.description, eMeal.description, "Description should be the same");
		assertNotNull(meal.slots, "Slots should not be null");
		assertEquals(meal.slots.size(), eMeal.slots.size(), "Slots size should be the same");
		assertEquals(meal.imageThumb, eMeal.imageThumb, "Image should be the same");
	}

	@Test
	void entityToModel_mapEntityWithNullValues_returnsCorrectModel() {
		final var eMeal = new EMeal();

		final var meal = EMealMapper.toModel(eMeal);

		assertNotNull(meal, "Meal should not be null");
		assertNull(meal.id, "Id should be null");
		assertNull(meal.name, "Name should be null");
		assertNull(meal.description, "Description should be null");
		assertNotNull(meal.slots, "Slots should not be null");
		assertTrue(meal.slots.isEmpty(), "Slots should be empty");
		assertNull(meal.imageThumb, "Image should be null");
	}

	/*
	 * toEntity(Meal)
	 * */
	@Test
	void modelToEntity_mapNullModel_returnsNullEntity() {
		final var meal = EMealMapper.toEntity(null);

		assertNull(meal, "Mapped meal entity should be null.");
	}

	@Test
	void modelToEntity_mapModelWithoutNullValues_returnsCorrectEntity() {
		final var slot1 = new Slot(1, "slot1");
		final var slot2 = new Slot(2, "slot2");
		final List<Slot> slotList= new ArrayList<Slot>();
		slotList.add(slot1);
		slotList.add(slot2);
		final var meal = new Meal(1, "name", "description", slotList, "image".getBytes());

		final var eMeal = EMealMapper.toEntity(meal);

		assertNotNull(eMeal, "EMeal should not be null");
		assertEquals(meal.id, eMeal.id, "Id should be the same");
		assertEquals(meal.name, eMeal.name, "Name should be the same");
		assertEquals(meal.description, eMeal.description, "Description should be the same");
		assertNotNull(meal.slots, "Slots should not be null");
		assertEquals(meal.slots.size(), eMeal.slots.size(), "Slots list size should be the same");
		assertEquals(meal.imageThumb, eMeal.imageThumb, "Image should be the same");
	}

	@Test
	void modelToEntity_mapModelWithNullValues_returnsCorrectEntity() {
		final var meal = new Meal(0, null, null, null, null);

		final var eMeal = EMealMapper.toEntity(meal);

		assertNotNull(eMeal, "EMeal should not be null");
		assertEquals(eMeal.id, 0, "Id should be 0");
		assertNull(eMeal.name, "Name should be null");
		assertNull(eMeal.description, "Description should be null");
		assertTrue(eMeal.slots.isEmpty(), "Slots should be empty");
		assertNull(eMeal.imageThumb, "Image should be null");
	}

}
