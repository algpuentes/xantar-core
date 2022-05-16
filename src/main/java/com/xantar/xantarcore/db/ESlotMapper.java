package com.xantar.xantarcore.db;

import com.xantar.xantarcore.models.Slot;

public class ESlotMapper {

	public static ESlot toEntity(Slot slot) {
		final var eSlot = new ESlot();
		eSlot.id = slot.id;
		eSlot.name = slot.name;
		return eSlot;
	}

	public static Slot toModel(ESlot eSlot) {
		return new Slot(eSlot.id, eSlot.name);
	}

}
