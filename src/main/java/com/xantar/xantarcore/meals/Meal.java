package com.xantar.xantarcore.meals;

import java.util.*;

public record Meal(Integer id, String name, String description, List<Slot> slots, byte[] imageThumb) {

	private  static final String EMPTY_NAME_FALLBACK = "New meal";

	public Meal(Integer id, String name, String description, List<Slot> slots, byte[] imageThumb) {
		this.id = id;
		this.name = Optional.ofNullable(name)
				.map(String::strip)
				.filter(n -> !n.isEmpty())
				.orElse(EMPTY_NAME_FALLBACK);
		this.description = description != null ? description.strip() : null;
		this.slots = Optional.ofNullable(slots)
				.map(ArrayList::new)
				.orElse(new ArrayList<>());
		this.imageThumb = imageThumb;
	}

	// Records with arrays must implement equals() to ensure deep equality
	@Override
	public boolean equals(Object o) {
		return Optional.ofNullable(o)
				.filter(other -> getClass() == other.getClass())
				.map(other -> (Meal) other)
				.filter(this::compareAttributes)
				.isPresent();
	}

	private boolean compareAttributes(Meal meal) {
		return Objects.equals(id, meal.id)
				&& Objects.equals(name, meal.name)
				&& Objects.equals(description, meal.description)
				&& Objects.equals(slots, meal.slots)
				&& Arrays.equals(imageThumb, meal.imageThumb);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(id, name, description, slots);
		result = 31 * result + Arrays.hashCode(imageThumb);
		return result;
	}

	@Override
	public String toString() {
		return "Meal{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", slots=" + slots +
				'}';
	}

	private Meal(MealBuilder builder) {
		this(builder.id, builder.name, builder.description, builder.slots, builder.imageThumb);
	}

	public static MealBuilder builder() {
		return new MealBuilder();
	}

	public static MealBuilder builder(Meal meal) {
		return builder()
				.withId(meal.id)
				.withName(meal.name)
				.withDescription(meal.description)
				.withImageThumb(meal.imageThumb)
				.withSlots(meal.slots);
	}

	public static class MealBuilder {
		private Integer	id;
		private String	name;
		private String	description;
		private List<Slot> slots;
		private byte[] imageThumb;

		private MealBuilder() {}

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
