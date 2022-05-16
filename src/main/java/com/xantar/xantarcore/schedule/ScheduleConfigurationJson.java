package com.xantar.xantarcore.schedule;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.xantar.xantarcore.db.ScheduleConfiguration;
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

	ScheduleConfigurationJson(ScheduleConfiguration scheduleConfiguration) {
		this.slot = new SlotResponseJson(scheduleConfiguration.slot.id, scheduleConfiguration.slot.name);
		this.meal = new MealResponseJson(scheduleConfiguration.meal);
	}

	ScheduleConfiguration toScheduleConfiguration() {
		return new ScheduleConfiguration(
				Optional.ofNullable(this.slot).map(SlotResponseJson::toSlot).orElse(null),
				Optional.ofNullable(this.meal).map(MealResponseJson::toMeal).orElse(null));
	}

}
