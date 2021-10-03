package com.xantar.xantarcore.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.xantar.xantarcore.models.Slot;

public class ESlotMapperTest {

	/*
	 * toModel(ESlot)
	 * */
	@Test
	void entityToModel_mapNullEntity_returnsNullModel() {
		final var slot = ESlotMapper.toModel(null);

		assertNull(slot, "Mapped slot model should be null.");
	}

	@Test
	void entityToModel_mapEntityWithoutNullValues_returnsCorrectModel() {
		final var eSlot = new ESlot();
		eSlot.id = 1;
		eSlot.name = "name";

		final var slot = ESlotMapper.toModel(eSlot);

		assertNotNull(slot, "Slot should not be null");
		assertEquals(slot.id, eSlot.id, "Id should be the same");
		assertEquals(slot.name, eSlot.name, "Name should be the same");
	}

	@Test
	void entityToModel_mapEntityWithNullValues_returnsCorrectModel() {
		final var eSlot = new ESlot();

		final var slot = ESlotMapper.toModel(eSlot);

		assertNotNull(slot, "Slot should not be null");
		assertEquals(slot.id, 0, "Id should be 0");
		assertNull(slot.name, "Name should be null");
	}


	/*
	 * toEntity(Slot)
	 * */
	@Test
	void modelToEntity_mapNullModel_returnsNullEntity() {
		final var slot = ESlotMapper.toEntity(null);

		assertNull(slot, "Mapped slot entity should be null.");
	}

	@Test
	void modelToEntity_mapModelWithoutNullValues_returnsCorrectEntity() {

		final var slot = new Slot(1, "name");

		final var eSlot = ESlotMapper.toEntity(slot);

		assertNotNull(eSlot, "ESlot should not be null");
		assertEquals(eSlot.id, slot.id, "Id should be the same");
		assertEquals(eSlot.name, slot.name, "Name should be the same");
	}

	@Test
	void modelToEntity_mapModelWithNullValues_returnsCorrectEntity() {
		final var slot = new Slot(0, null);

		final var eSlot = ESlotMapper.toEntity(slot);

		assertNotNull(eSlot, "ESlot should not be null");
		assertEquals(eSlot.id, 0, "Id should be 0");
		assertNull(eSlot.name, "Name should be null");
	}

}
