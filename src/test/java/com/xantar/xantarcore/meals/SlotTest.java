package com.xantar.xantarcore.meals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SlotTest {

    /**
     * constructor
     * */
    @ParameterizedTest
    @ValueSource(strings = { "Name", "Name with spaces" })
    void toSlot_instantiatesSlot(String name) {
        var slot = new Slot(1, name);

        assertEquals(1, slot.id());
        assertEquals(name, slot.name());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "","   " })
    void toSlot_throwsIllegalArgumentException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Slot(1, name));
    }



}