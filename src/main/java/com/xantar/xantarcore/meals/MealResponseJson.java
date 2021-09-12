package com.xantar.xantarcore.meals;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MealResponseJson {

	public final UUID	id;
	public final String	name;
	public final String	description;
	public final List<String> slots;

	@JsonCreator
	public MealResponseJson(UUID id, String name, String description, List<String> slots) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.slots = slots;
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"id\":\"" + this.id + "\",");
		sb.append("\"name\":\"" + this.name + "\",");
		sb.append("\"description\":\"" + this.description + "\"");
		//TODO: append slots
		sb.append("}");

		return sb.toString();
	}
}
