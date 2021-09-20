package com.xantar.xantarcore.models;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.xantar.xantarcore.utils.Base64Utils;

public class Meal {

	public final int	id;
	public final String	name;
	public final String	description;
	public final List<Slot> slots;
	public final byte[] imageThumb;

	public Meal(int id, String name, String description, List<Slot> slots, byte[] imageThumb) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.slots = slots;
		this.imageThumb = imageThumb;
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
		return Objects.hash(this.id, this.name, this.description, this.slots, this.imageThumb);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("id=" + this.id + ", ");
		sb.append("name=" + this.name + ", ");
		sb.append("description=" + this.description + ", ");
		sb.append("slots=" + this.slotsListToString() + ", ");
		sb.append(Base64Utils.encodeImage(this.imageThumb));
		sb.append("]");

		return sb.toString();
	}

	private String slotsListToString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("{");
		if(this.slots != null) {
			sb.append(this.slots.stream()
					.map(slot -> slot.toString())
					.collect(Collectors.joining(",")));
		}
		sb.append("}");

		return sb.toString();
	}

	private boolean compareAttributes(Meal meal) {
		return this.id == meal.id
				&& Objects.equals(this.name, meal.name)
				&& Objects.equals(this.description, meal.description)
				&& Objects.equals(this.slots, meal.slots)
				&& Objects.equals(this.imageThumb, meal.imageThumb);
	}

}
