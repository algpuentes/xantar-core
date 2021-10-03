package com.xantar.xantarcore.models;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Meal {

	public final Integer	id;
	public final String	name;
	public final String	description;
	public final List<Slot> slots;
	public final byte[] imageThumb;

	public Meal(Integer id, String name, String description, List<Slot> slots, byte[] imageThumb) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.slots = slots;
		this.imageThumb = imageThumb;
	}

	public Meal(MealBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.slots = builder.slots;
		this.imageThumb = builder.imageThumb;
	}

	@Override
	public boolean equals(Object other) {
		return Optional.ofNullable(other)
				.filter(Meal.class::isInstance)
				.map(Meal.class::cast)
				.filter(object -> this.compareAttributes(object))
				.isPresent();
	}

	private boolean compareAttributes(Meal meal) {
		return Objects.equals(this.id, meal.id)
				&& Objects.equals(this.name, meal.name)
				&& Objects.equals(this.description, meal.description)
				&& Objects.equals(this.slots, meal.slots)
				&& Objects.equals(this.imageThumb, meal.imageThumb);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.description, this.slots, this.imageThumb);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id=" + this.id + ", ");
		sb.append("name=" + this.name + ", ");
		sb.append("description=" + this.description + ", ");
		sb.append("slots=" + this.slotsListToString());
		sb.append("}");

		return sb.toString();
	}

	private String slotsListToString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("[");
		if(this.slots != null) {
			sb.append(this.slots.stream()
					.map(slot -> slot.toString())
					.collect(Collectors.joining(", ")));
		}
		sb.append("]");

		return sb.toString();
	}

	public static class MealBuilder {
		private Integer	id;
		private String	name;
		private String	description;
		private List<Slot> slots;
		private byte[] imageThumb;

		public MealBuilder() {}

		public Meal build() {
			return new Meal(this);
		}

		public MealBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public MealBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public MealBuilder withDescription(String description) {
			this.description = description;
			return this;
		}

		public MealBuilder withSlots(List<Slot> slots) {
			this.slots = slots;
			return this;
		}

		public MealBuilder withImageThumb(byte[] imageThumb) {
			this.imageThumb = imageThumb;
			return this;
		}

	}

}
