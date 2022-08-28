package com.xantar.xantarcore.meals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record SlotResponseJson(int id, String name) {

	public Slot toSlot() {
		return new Slot(this.id, this.name);
	}
}
