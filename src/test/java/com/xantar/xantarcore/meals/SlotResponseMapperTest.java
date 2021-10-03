package com.xantar.xantarcore.meals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.xantar.xantarcore.models.Slot;

public class SlotResponseMapperTest {
	/*
	 * toModel(SlotResponseJson)
	 * */
	@Test
	void entityToModel_mapNullJson_returnsNullModel() {
		final var slot = SlotResponseMapper.toModel(null);

		assertNull(slot, "Mapped slot model should be null.");
	}

	@Test
	void entityToModel_mapJsonWithoutNullValues_returnsCorrectModel() {
		final var jSlot = new SlotResponseJson(0, "slot");

		final var slot = SlotResponseMapper.toModel(jSlot);

		assertNotNull(slot, "Slot should not be null");
		assertEquals(jSlot.id, slot.id, "Id should be the same");
		assertEquals(jSlot.name, slot.name, "Name should be the same");
	}

	@Test
	void entityToModel_mapJsonWithNullValues_returnsCorrectModel() {
		final var slot = new SlotResponseJson(1, null);

		final var jSlot = SlotResponseMapper.toModel(slot);

		assertNotNull(jSlot, "Slot should not be null");
		assertEquals(slot.id, jSlot.id, "Id should be 0");
		assertNull(jSlot.name, "Name should be null");
	}


	/*
	 * toEntity(Slot)
	 * */
	@Test
	void modelToJson_mapNullModel_returnsNullJson() {
		final var slot = SlotResponseMapper.toJsonResponse(null);

		assertNull(slot, "Mapped slot entity should be null.");
	}

	@Test
	void modelToJson_mapModelWithoutNullValues_returnsCorrectJson() {

		final var slot = new Slot(1, "name");

		final var jSlot = SlotResponseMapper.toJsonResponse(slot);

		assertNotNull(jSlot, "Slot should not be null");
		assertEquals(slot.id, jSlot.id, "Id should be the same");
		assertEquals(slot.name, jSlot.name, "Name should be the same");
	}

	@Test
	void modelToJson_mapModelWithNullValues_returnsCorrectJson() {
		final var slot = new Slot(0, null);

		final var jSlot = SlotResponseMapper.toJsonResponse(slot);

		assertNotNull(jSlot, "Slot should not be null");
		assertEquals(jSlot.id, slot.id, "Id should be 0");
		assertNull(jSlot.name, "Name should be null");
	}

}
