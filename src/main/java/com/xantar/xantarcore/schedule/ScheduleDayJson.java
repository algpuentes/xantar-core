package com.xantar.xantarcore.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class represents a DTO of the resource <code>ScheduleDayJson</code>. Its attributes have public visibility due to Jackson requirements,
 * but this class should not be used out of the scope of this package.
 * */
@JsonInclude(JsonInclude.Include.NON_NULL)
record ScheduleDayJson(Integer id, Long timestamp, List<ScheduleConfigurationJson> configurations) {

	ScheduleDayJson(DaySchedule daySchedule) {
		this(daySchedule.id(),
				daySchedule.timestamp(),
				daySchedule.configurations().stream()
						.map(ScheduleConfigurationJson::new)
					.collect(Collectors.toList()));
	}

	DaySchedule toScheduleDay() {
		return new DaySchedule(this.id,
				this.timestamp,
				Optional.ofNullable(this.configurations)
				.map(list -> list.stream()
						.map(ScheduleConfigurationJson::toScheduleConfiguration)
						.collect(Collectors.toList()))
				.orElse(new ArrayList<>()));
	}
}
