package com.xantar.xantarcore.models;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Meal {

	public final int	id;
	public final String	name;
	public final String	description;
	public final List<Slot> slots;

	public Meal(int id, String name, String description, List<Slot> slots) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.slots = slots;
	}

	@Override
	public boolean equals(Object other) {
		return Optional.ofNullable(other)
				.filter(Meal.class::isInstance)
				.map(Meal.class::cast)
				.filter(object -> this.compareAttributes(object))
				.isPresent();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.description, this.slots);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("id=" + this.id + ", ");
		sb.append("name=" + this.name + ", ");
		sb.append("description=" + this.description);
		//TODO: append slots
		sb.append("]");

		return sb.toString();
	}

	private boolean compareAttributes(Meal meal) {
		return this.id == meal.id
				&& this.name == meal.name
				&& this.description == meal.description
				//TODO: compare slots
				;
	}
}
