package com.xantar.xantarcore.meals;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MealResponseJson {

	public final int	id;
	public final String	name;
	public final String	description;
	public final List<SlotResponseJson> slots;
	public final String imageThumb;

	@JsonCreator
	public MealResponseJson(int id, String name, String description, List<SlotResponseJson> slots, String imageThumb) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.slots = slots;
		this.imageThumb = imageThumb;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"id\":" + this.id + ",");
		sb.append("\"name\":\"" + this.name + "\",");
		sb.append("\"description\":\"" + this.description + "\",");
		sb.append("\"slots\":");
		sb.append(this.slotsListToString(this.slots));
		sb.append(",");
		sb.append("\"imageThumb\":\"" + this.imageThumb + "\"");
		sb.append("}");

		return sb.toString();
	}

	private String slotsListToString(List<SlotResponseJson> slots) {
		final StringBuilder sb = new StringBuilder();

		sb.append("[");
		if(slots != null) {
			this.slots.stream().forEach(slot -> sb.append(slot.toString()+","));
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		sb.append("]");

		return sb.toString();
	}
}
