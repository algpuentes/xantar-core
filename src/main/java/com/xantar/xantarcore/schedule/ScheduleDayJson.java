package com.xantar.xantarcore.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.xantar.xantarcore.meals.MealResponseJson;
import com.xantar.xantarcore.meals.SlotResponseJson;

public class ScheduleDayJson {

	public final Integer id;
	public final Long timestamp;
	public final List<ScheduleConfigurationJson> configurations;

	public ScheduleDayJson(Integer id, Long timestamp, List<ScheduleConfigurationJson> configurations) {
		this.id = id;
		this.timestamp = timestamp;
		this.configurations = configurations;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		final List<String> list = new ArrayList<String>();
		sb.append("{");
		if(this.id != null) {
			list.add("id:" + this.id);
		}
		if(this.timestamp != null) {
			list.add("timestamp:" + this.timestamp);
		}
		if(this.configurations != null) {
			list.add("configurations:" + this.configsListToString());
		}
		sb.append(list.stream().collect(Collectors.joining(",")));
		sb.append("}");

		return sb.toString();
	}

	private String configsListToString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("[");
		if(this.configurations != null) {
			sb.append(this.configurations.stream()
					.map(configuration -> configuration.toString())
					.collect(Collectors.joining(", ")));
		}
		sb.append("]");

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


	private boolean compareAttributes(ScheduleDayJson other) {
		return Objects.equals(this.id, other.id)
				&& Objects.equals(this.timestamp, other.timestamp)
				&& Objects.equals(this.configurations, other.configurations);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.timestamp, this.configurations);
	}

	public static void main(String[] args) {
		final var config = new ScheduleConfigurationJson(new SlotResponseJson(0, "slot"), new MealResponseJson(1, "meal", "description", Collections.singletonList(new SlotResponseJson(0, "slot")), "image"));
		final var day = new ScheduleDayJson(1, System.currentTimeMillis(), Collections.singletonList(config));
		System.out.println(config.toString());
		System.out.println(day.toString());
	}
}
