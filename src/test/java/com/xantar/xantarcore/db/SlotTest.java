package com.xantar.xantarcore.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SlotTest {

	/*
	 * new Slot(ESlot)
	 * */
	@Test
	void entityToModel_mapNullEntity_throwsException() {
		assertThrows(NullPointerException.class, () -> new Slot(null));
	}

	@Test
	void entityToModel_mapEntity_returnsCorrectModel() {
		final var eSlot = new ESlot(1, "name");

		final var slot = new Slot(eSlot);

		assertNotNull(slot, "Slot should not be null");
		assertEquals(eSlot.id, slot.id, "Id should be the same");
		assertEquals(eSlot.name, slot.name, "Name should be the same");
	}

	@Test
	void entityToModel_entityWithNullName_throwsException() {
		final var eSlot = new ESlot(1, null);

		assertThrows(NullPointerException.class, () -> new Slot(eSlot));
	}

	@Test
	void entityToModel_entityWithEmptyName_throwsException() {
		final var eSlot = new ESlot(1, "");

		assertThrows(NullPointerException.class, () -> new Slot(eSlot));
	}

	@Test
	void entityToModel_entityWithBlankName_throwsException() {
		final var eSlot = new ESlot(1, "   ");

		assertThrows(NullPointerException.class, () -> new Slot(eSlot));
	}
}
