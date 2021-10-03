package com.xantar.xantarcore.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.xantar.xantarcore.meals.MealResponseJson;
import com.xantar.xantarcore.meals.SlotResponseJson;


public class ScheduleConfigurationJson {

	public final SlotResponseJson slot;
	public final MealResponseJson meal;


	public ScheduleConfigurationJson(SlotResponseJson slot, MealResponseJson meal) {
		this.slot = slot;
		this.meal = meal;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		final List<String> list = new ArrayList<>();
		sb.append("{");
		if(this.slot != null) {
			list.add("slot:" + this.slot.toString());
		}
		if(this.meal != null) {
			list.add("meal:" + this.meal.toString());
		}
		sb.append(list.stream().collect(Collectors.joining(",")));
		sb.append("}");

		return sb.toString();
	}

	@Override
	public boolean equals(Object other) {
		return Optional.ofNullable(other)
				.filter(this.getClass()::isInstance)
				.map(this.getClass()::cast)
				.filter(this::compareAttributes)
				.isPresent();
	}


	private boolean compareAttributes(ScheduleConfigurationJson other) {
		return Objects.equals(this.slot, other.slot)
				&& Objects.equals(this.meal, other.meal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.slot, this.meal);
	}

}
