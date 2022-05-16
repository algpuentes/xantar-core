package com.xantar.xantarcore.meals;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xantar.xantarcore.db.Meal;
import com.xantar.xantarcore.db.Slot;
import com.xantar.xantarcore.utils.Base64Utils;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MealResponseJson {

	public final Integer	id;
	public final String	name;
	public final String	description;
	public final List<SlotResponseJson> slots;
	public final String imageThumb;

	@JsonCreator
	public MealResponseJson(Integer id, String name, String description, List<SlotResponseJson> slots, String imageThumb) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.slots = slots;
		this.imageThumb = imageThumb;
	}

	public MealResponseJson(Meal meal) {
		Objects.requireNonNull(meal);

		this.id = meal.id;
		this.name = meal.name;
		this.description = meal.description;
		this.slots = Optional.ofNullable(meal.slots)
				.map(slotsList -> slotsList.stream().map(slot -> new SlotResponseJson(slot.id, slot.name)).collect(Collectors.toList()))
				.orElse(new ArrayList<SlotResponseJson>());
		this.imageThumb = Base64Utils.encodeImage(meal.imageThumb);
	}

	public Meal toMeal() {
		return Meal.builder()
				.withId(this.id)
				.withName(this.name)
				.withDescription(this.description)
				.withSlots(Optional.ofNullable(this.slots)
						.map(s -> s.stream()
								.map(SlotResponseJson::toSlot)
								.collect(Collectors.toList()))
						.orElse(new ArrayList<Slot>()))
				.withImageThumb(Base64Utils.decodeImage(this.imageThumb))
				.build();
	}

	private MealResponseJson(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.slots = builder.slots;
		this.imageThumb = builder.imageThumb;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Integer	id;
		private String	name;
		private String	description;
		private List<SlotResponseJson> slots;
		private String imageThumb;

		private Builder() {}

		public MealResponseJson build() {
			return new MealResponseJson(this);
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder withSlots(List<SlotResponseJson> slots) {
			this.slots = slots;
			return this;
		}

		public Builder withImageThumb(String imageThumb) {
			this.imageThumb = imageThumb;
			return this;
		}

	}
}
