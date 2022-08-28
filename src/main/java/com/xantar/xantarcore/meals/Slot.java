package com.xantar.xantarcore.meals;

import java.util.Optional;

public record Slot(int id, String name) {

	public Slot(int id, String name) {
		this.id = id;
		this.name = Optional.ofNullable(name)
				.filter(s -> !s.isBlank())
				.orElseThrow(() -> new IllegalArgumentException("Slot name must not be null or empty!)"));
	}

}
