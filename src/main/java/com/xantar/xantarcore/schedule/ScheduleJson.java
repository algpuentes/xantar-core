package com.xantar.xantarcore.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ScheduleJson {

	public final Integer id;
	public final List<ScheduleDayJson> days;

	public ScheduleJson(List<ScheduleDayJson> days) {
		this.id = null;
		this.days = new ArrayList<ScheduleDayJson>(days);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		final List<String> list = new ArrayList<String>();
		sb.append("{");
		if(this.id != null) {
			list.add("id:" + this.id);
		}
		if(this.days != null) {
			list.add("configurations:" + this.daysListToString());
		}
		sb.append(list.stream().collect(Collectors.joining(",")));
		sb.append("}");

		return sb.toString();
	}

	private String daysListToString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("[");
		if(this.days != null) {
			sb.append(this.days.stream()
					.map(day -> day.toString())
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


	private boolean compareAttributes(ScheduleJson other) {
		return Objects.equals(this.id, other.id)
				&& Objects.equals(this.days, other.days);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.days);
	}

}
