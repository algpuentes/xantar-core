package com.xantar.xantarcore.schedule;

import java.util.stream.Collectors;

import com.xantar.xantarcore.models.ScheduleDay;

public class ScheduleDayJsonMapper {

	public static ScheduleDay toModel(ScheduleDayJson jDay) {
		return new ScheduleDay(
				jDay.id,
				jDay.timestamp,
				jDay.configurations.stream().map((jConfig) -> ScheduleConfigurationJsonMapper.toModel(jConfig)).collect(Collectors.toList()));
	}

	public static ScheduleDayJson toJson(ScheduleDay day) {
		return new ScheduleDayJson(
				day.id,
				day.timestamp,
				day.configurations.stream().map((config) -> ScheduleConfigurationJsonMapper.toJson(config)).collect(Collectors.toList()));
	}

}
