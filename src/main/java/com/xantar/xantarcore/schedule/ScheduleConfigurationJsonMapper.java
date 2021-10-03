package com.xantar.xantarcore.schedule;

import com.xantar.xantarcore.meals.MealResponseMapper;
import com.xantar.xantarcore.meals.SlotResponseMapper;
import com.xantar.xantarcore.models.ScheduleConfiguration;

public class ScheduleConfigurationJsonMapper {

	public static ScheduleConfiguration toModel(ScheduleConfigurationJson jConfiguration) {
		return new ScheduleConfiguration(
				SlotResponseMapper.toModel(jConfiguration.slot),
				MealResponseMapper.toModel(jConfiguration.meal));
	}

	public static ScheduleConfigurationJson toJson(ScheduleConfiguration configuration) {
		return new ScheduleConfigurationJson(
				SlotResponseMapper.toJsonResponse(configuration.slot),
				MealResponseMapper.toJsonResponse(configuration.meal));
	}

}
