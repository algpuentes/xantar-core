package com.xantar.xantarcore.meals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xantar.xantarcore.db.Slot;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SlotResponseJson {

	public final int id;
	public final String name;

	public Slot toSlot() {
		return new Slot(this.id, this.name);
	}

	@JsonCreator
	public SlotResponseJson(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"id\":" + this.id + ",");
		sb.append("\"name\":\"" + this.name +"\"");
		sb.append("}");

		return sb.toString();
	}

}
