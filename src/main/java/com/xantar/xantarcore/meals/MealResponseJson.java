package com.xantar.xantarcore.meals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xantar.xantarcore.common.utils.Base64Utils;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record MealResponseJson(Integer id, String name, String description, List<SlotResponseJson> slots, String imageThumb) {

	public MealResponseJson(Meal meal) {
		this(meal.id(),
			meal.name(),
			meal.description(),
			Optional.ofNullable(meal.slots())
				.map(slotsList -> slotsList.stream()
						.map(slot -> new SlotResponseJson(slot.id(),slot.name()))
						.collect(Collectors.toList()))
				.orElse(new ArrayList<>()),
			Base64Utils.encodeImage(meal.imageThumb()));
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
						.orElse(new ArrayList<>()))
				.withImageThumb(Base64Utils.decodeImage(this.imageThumb))
				.build();
	}

	private MealResponseJson(Builder builder) {
		this(builder.id, builder.name, builder.description, builder.slots, builder.imageThumb);
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
