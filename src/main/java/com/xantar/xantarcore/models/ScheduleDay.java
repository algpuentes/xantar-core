package com.xantar.xantarcore.models;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScheduleDay {

	public final Integer id;
	public final Long timestamp;
	public final List<ScheduleConfiguration> configurations;

	public ScheduleDay(Integer id, Long timestamp, List<ScheduleConfiguration> configurations) {
		this.id = id;
		this.timestamp = timestamp;
		this.configurations = configurations;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id=" + this.id + ", ");
		sb.append("timestamp=" + this.timestamp + ", ");
		sb.append("configurations=[" + this.configsListToString() + "]");
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


	private boolean compareAttributes(ScheduleDay other) {
		return Objects.equals(this.id, other.id)
				&& Objects.equals(this.timestamp, other.timestamp)
				&& Objects.equals(this.configurations, other.configurations);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.timestamp, this.configurations);
	}

}
