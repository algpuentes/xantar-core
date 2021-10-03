package com.xantar.xantarcore.models;

import java.util.Objects;
import java.util.Optional;

public class Slot {

	public final int id;
	public final String name;

	public Slot(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public boolean equals(Object other) {
		return Optional.ofNullable(other)
				.filter(Slot.class::isInstance)
				.map(Slot.class::cast)
				.filter(object -> this.compareAttributes(object))
				.isPresent();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id=" + this.id + ", ");
		sb.append("name=" + this.name + "}");

		return sb.toString();
	}

	private boolean compareAttributes(Slot slot) {
		return this.id == slot.id
				&& Objects.equals(this.name, slot.name);
	}

}
