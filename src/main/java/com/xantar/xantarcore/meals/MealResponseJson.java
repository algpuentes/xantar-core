package com.xantar.xantarcore.meals;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
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
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"id\":" + this.id + ",");
		sb.append("\"name\":\"" + this.name + "\",");
		sb.append("\"description\":\"" + this.description + "\",");
		sb.append("\"slots\":");
		sb.append(this.slotsListToString(this.slots));
		sb.append(",");
		sb.append("\"imageThumb\":\"" + this.imageThumb + "\"");
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
