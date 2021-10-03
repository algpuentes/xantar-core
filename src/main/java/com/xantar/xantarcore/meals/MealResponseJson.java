package com.xantar.xantarcore.meals;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.tomcat.util.buf.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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

	public MealResponseJson(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.slots = builder.slots;
		this.imageThumb = builder.imageThumb;
	}

	@Override
	public String toString() {
		final var list = new ArrayList<String>();

		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		if(this.id != null) {
			list.add("\"id\":" + this.id);
		}
		if(this.name != null) {
			list.add("\"name\":\"" + this.name + "\"");
		}
		if(this.description != null) {
			list.add("\"description\":\"" + this.description + "\"");
		}
		if(this.slots != null) {
			list.add("\"slots\":" + this.slotsListToString(this.slots));
		}
		if(this.imageThumb != null) {
			list.add("\"imageThumb\":\"" + this.imageThumb + "\"");
		}
		sb.append(StringUtils.join(list, ','));
		sb.append("}");

		return sb.toString();
	}

	private String slotsListToString(List<SlotResponseJson> slots) {
		final StringBuilder sb = new StringBuilder();

		sb.append("[");
		if(slots != null) {
			sb.append(slots.stream()
					.map(slot -> slot.toString())
					.collect(Collectors.joining(",")));
		}
		sb.append("]");

		return sb.toString();
	}

	@Override
	public boolean equals(Object other) {
		return Optional.ofNullable(other)
				.filter(MealResponseJson.class::isInstance)
				.map(MealResponseJson.class::cast)
				.filter(object -> this.compareAttributes(object))
				.isPresent();
	}

	private boolean compareAttributes(MealResponseJson meal) {
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


	public static class Builder {
		private Integer	id;
		private String	name;
		private String	description;
		private List<SlotResponseJson> slots;
		private String imageThumb;

		public Builder() {}

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
