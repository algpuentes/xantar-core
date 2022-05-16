package com.xantar.xantarcore.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Meal {

	public final Integer id;
	public final String	name;
	public final String	description;
	public final List<Slot> slots;
	public final byte[] imageThumb;

	public Meal(Integer id, String name, String description, List<Slot> slots, byte[] imageThumb) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.slots = Optional.ofNullable(slots).map(list -> new ArrayList<Slot>(list)).orElse(new ArrayList<Slot>());
		this.imageThumb = imageThumb;
	}

	Meal(EMeal eMeal) {
		Objects.requireNonNull(eMeal);

		this.id = eMeal.id;
		this.name = eMeal.name;
		this.description = eMeal.description;
		this.slots = Optional.ofNullable(eMeal.slots)
				.map(slots -> slots.stream().map(eSlot -> new Slot(eSlot)).collect(Collectors.toList()))
				.orElse(new ArrayList<Slot>());
		this.imageThumb = eMeal.imageThumb;
	}

	private Meal(MealBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.slots =  Optional.ofNullable(builder.slots).map(list -> new ArrayList<Slot>(list)).orElse(new ArrayList<Slot>());
		this.imageThumb = builder.imageThumb;
	}

	public static MealBuilder builder() {
		return new MealBuilder();
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

	@Override
	public boolean equals(Object other) {
		return Optional.ofNullable(other)
				.filter(value -> Objects.equals(Meal.class, value.getClass()))
				.map(Meal.class::cast)
				.filter(object -> this.compareAttributes(object))
				.isPresent();
	}

	private boolean compareAttributes(Meal meal) {
		return Objects.equals(this.id, meal.id)
				&& Objects.equals(this.name, meal.name)
				&& Objects.equals(this.description, meal.description)
				&& Objects.equals(this.slots, meal.slots)
				&& Arrays.equals(this.imageThumb, meal.imageThumb);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.description, this.slots, this.imageThumb);
	}

}
