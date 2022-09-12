package com.xantar.xantarcore.db;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ESlotTest {

	/**
	 * toSlot()
	 * */
	@ParameterizedTest
	@ValueSource(strings = { "Name", "Name with spaces" })
	void toSlot_instantiatesSlot(String name) {
		var eSlot = new ESlot();
		eSlot.id = 1;
		eSlot.name = name;

		var slot = eSlot.toSlot();

		assertEquals(1, slot.id());
		assertEquals(name, slot.name());
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "","   " })
	void toSlot_throwsIllegalArgumentException(String name) {
		var eSlot = new ESlot();
		eSlot.id = 1;
		eSlot.name = name;

		assertThrows(IllegalArgumentException.class, eSlot::toSlot);
	}


}
