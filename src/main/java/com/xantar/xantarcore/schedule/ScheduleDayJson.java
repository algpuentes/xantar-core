package com.xantar.xantarcore.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.xantar.xantarcore.db.ScheduleConfiguration;
import com.xantar.xantarcore.db.ScheduleDay;

/**
 * This class represents a DTO of the resource <code>ScheduleDayJson</code>. Its attributes have public visibility due to Jackson requirements,
 * but this class should not be used out of the scope of this package.
 * */
class ScheduleDayJson {

	public final Integer id;
	public final Long timestamp;
	public final List<ScheduleConfigurationJson> configurations;

	@JsonCreator
	public ScheduleDayJson(Integer id, Long timestamp, List<ScheduleConfigurationJson> configurations) {
		this.id = id;
		this.timestamp = timestamp;
		this.configurations = configurations;
	}

	ScheduleDayJson(ScheduleDay scheduleDay) {
		this.id = scheduleDay.id;
		this.timestamp = scheduleDay.timestamp;
		this.configurations = Optional.ofNullable(scheduleDay.configurations)
				.map(configurations -> configurations.stream().map(conf -> new ScheduleConfigurationJson(conf)).collect(Collectors.toList()))
				.orElse(null);
	}

	ScheduleDay toScheduleDay() {
		return new ScheduleDay(this.id,
				this.timestamp,
				Optional.ofNullable(this.configurations)
				.map(list -> list.stream()
						.map(config -> config.toScheduleConfiguration())
						.collect(Collectors.toList()))
				.orElse(new ArrayList<ScheduleConfiguration>()));
	}

}
