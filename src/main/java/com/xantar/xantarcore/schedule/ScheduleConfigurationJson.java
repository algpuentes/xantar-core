package com.xantar.xantarcore.schedule;

import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.xantar.xantarcore.meals.MealResponseJson;
import com.xantar.xantarcore.meals.SlotResponseJson;

/**
 * This class represents a DTO of the resource <code>ScheduleConfiguration</code>. Its attributes have public visibility due to Jackson requirements,
 * but this class should not be used out of the scope of this package.
 * */
class ScheduleConfigurationJson {

	public final SlotResponseJson slot;
	public final MealResponseJson meal;

	@JsonCreator
	public ScheduleConfigurationJson(SlotResponseJson slot, MealResponseJson meal) {
		this.slot = slot;
		this.meal = meal;
	}

	ScheduleConfigurationJson(SlotSchedule slotSchedule) {
		this.slot = new SlotResponseJson(slotSchedule.slot().id(), slotSchedule.slot().name());
		this.meal = new MealResponseJson(slotSchedule.meal());
	}

	SlotSchedule toScheduleConfiguration() {
		return new SlotSchedule(
				Optional.ofNullable(this.slot).map(SlotResponseJson::toSlot).orElse(null),
				Optional.ofNullable(this.meal).map(MealResponseJson::toMeal).orElse(null));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ScheduleConfigurationJson that = (ScheduleConfigurationJson) o;
		return Objects.equals(slot, that.slot) && Objects.equals(meal, that.meal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(slot, meal);
	}
}
