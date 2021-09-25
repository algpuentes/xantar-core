package com.xantar.xantarcore.meals;

import com.xantar.xantarcore.models.Slot;

public class SlotResponseMapper {

	public static Slot toModel(SlotResponseJson jSlot) {
		if(jSlot == null) {
			return null;
		}

		return new Slot(jSlot.id, jSlot.name);

	}

	public static SlotResponseJson toJsonResponse(Slot slot) {
		if(slot == null) {
			return null;
		}
		return new SlotResponseJson(slot.id, slot.name);
	}

}
