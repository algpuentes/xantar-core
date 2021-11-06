package com.xantar.xantarcore.models;

import java.util.Objects;
import java.util.Optional;

public class ScheduleConfiguration {

	public final Slot slot;
	public Meal meal;


	public ScheduleConfiguration(Slot slot, Meal meal) {
		this.slot = slot;
		this.meal = meal;
	}

	public Integer retrieveSlotId() {
		return Optional.ofNullable(this.slot.id).orElse(null);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("slot=" + this.slot.toString() + ", ");
		sb.append("meal=" + this.meal.toString());
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


	private boolean compareAttributes(ScheduleConfiguration other) {
		return Objects.equals(this.slot, other.slot)
				&& Objects.equals(this.meal, other.meal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.slot, this.meal);
	}

}
