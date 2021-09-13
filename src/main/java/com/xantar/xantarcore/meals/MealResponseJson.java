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
	public final List<String> slots;

	@JsonCreator
	public MealResponseJson(int id, String name, String description, List<String> slots) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.slots = slots;
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"id\":" + this.id + ",");
		sb.append("\"name\":\"" + this.name + "\",");
		sb.append("\"description\":\"" + this.description + "\"");
		//TODO: append slots
		sb.append("}");

		return sb.toString();
	}
}
