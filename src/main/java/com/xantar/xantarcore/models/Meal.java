package com.xantar.xantarcore.models;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Meal {

	public final UUID	id;
	public final String	name;
	public final String	description;

	public Meal(UUID id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
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
		return Objects.hash(this.id, this.name, this.description);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("id=" + this.id + ", ");
		sb.append("name=" + this.name + ", ");
		sb.append("description=" + this.description);
		sb.append("]");

		return sb.toString();
	}

	private boolean compareAttributes(Meal meal) {
		return this.id == meal.id
				&& this.name == meal.name
				&& this.description == meal.description;
	}
}
