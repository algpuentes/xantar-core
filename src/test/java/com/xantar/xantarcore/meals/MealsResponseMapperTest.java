package com.xantar.xantarcore.meals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.xantar.xantarcore.models.Meal;
import com.xantar.xantarcore.models.Slot;
import com.xantar.xantarcore.utils.Base64Utils;

public class MealsResponseMapperTest {
	/*
	 * toModel(MealResponseJson)
	 * */
	@Test
	void entityToModel_mapNullEntity_returnsNullModel() {
		final var meal = MealResponseMapper.toModel(null);

		assertNull(meal, "Mapped meal model should be null.");
	}

	@Test
	void entityToModel_mapEntityWithoutNullValues_returnsCorrectModel() {

		final var jMeal = new MealResponseJson.Builder()
				.withId(1)
				.withName("name")
				.withDescription("decription")
				.withImageThumb(Base64Utils.encodeImage("imageToDecodeInMapper".getBytes()))
				.withSlots(new ArrayList<SlotResponseJson>())
				.build();

		jMeal.slots.add(new SlotResponseJson(1, "slot1"));
		jMeal.slots.add(new SlotResponseJson(2, "slot2"));

		final var meal = MealResponseMapper.toModel(jMeal);

		assertNotNull(meal, "Meal should not be null");
		assertEquals(jMeal.id, jMeal.id, "Id should be the same");
		assertEquals(jMeal.name, jMeal.name, "Name should be the same");
		assertEquals(jMeal.description, jMeal.description, "Description should be the same");
		assertNotNull(jMeal.slots, "Slots should not be null");
		assertEquals(jMeal.slots.size(), jMeal.slots.size(), "Slots size should be the same");
		assertEquals(jMeal.imageThumb, Base64Utils.encodeImage(meal.imageThumb), "Image should be the same");
	}

	@Test
	void entityToModel_mapEntityWithNullValues_returnsCorrectModel() {
		final var jMeal = new MealResponseJson.Builder().build();

		final var meal = MealResponseMapper.toModel(jMeal);

		assertNotNull(meal, "Meal should not be null");
		assertNull(meal.id, "Id should be null");
		assertNull(meal.name, "Name should be null");
		assertNull(meal.description, "Description should be null");
		assertNotNull(meal.slots, "Slots should not be null");
		assertTrue(meal.slots.isEmpty(), "Slots should be empty");
		assertNull(meal.imageThumb, "Image should be null");
	}

	/*
	 * toJsonResponse(Meal)
	 * */
	@Test
	void modelToJsonResponse_mapNullModel_returnsNullJsonResponse() {
		final var meal = MealResponseMapper.toJsonResponse(null);

		assertNull(meal, "Mapped meal json should be null.");
	}

	@Test
	void modelToJsonResponse_mapModelWithoutNullValues_returnsCorrectEntity() {
		final var slot1 = new Slot(1, "slot1");
		final var slot2 = new Slot(2, "slot2");
		final var slotList= new ArrayList<Slot>();
		slotList.add(slot1);
		slotList.add(slot2);
		final var meal = new Meal(1, "name", "description", slotList, "image".getBytes());

		final var jMeal = MealResponseMapper.toJsonResponse(meal);

		assertNotNull(jMeal, "MealResponseJson should not be null");
		assertEquals(meal.id, jMeal.id, "Id should be the same");
		assertEquals(meal.name, jMeal.name, "Name should be the same");
		assertEquals(meal.description, jMeal.description, "Description should be the same");
		assertNotNull(meal.slots, "Slots should not be null");
		assertEquals(meal.slots.size(), jMeal.slots.size(), "Slots list size should be the same");
		assertEquals(Base64Utils.encodeImage(meal.imageThumb), jMeal.imageThumb, "Image should be the same");
	}

	@Test
	void modelToJsonResponse_mapModelWithNullValues_returnsCorrectEntity() {
		final var meal = new Meal(0, null, null, null, null);

		final var jMeal = MealResponseMapper.toJsonResponse(meal);

		assertNotNull(jMeal, "MealResponseJson should not be null");
		assertEquals(jMeal.id, 0, "Id should be 0");
		assertNull(jMeal.name, "Name should be null");
		assertNull(jMeal.description, "Description should be null");
		assertTrue(jMeal.slots.isEmpty(), "Slots should be empty");
		assertNull(jMeal.imageThumb, "Image should be null");
	}

}
