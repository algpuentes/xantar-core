package com.xantar.xantarcore.db;

import java.util.Objects;
import java.util.Optional;

public class Slot {

	public final int id;
	public final String name;

	public Slot(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Slot(ESlot eSlot) {
		Objects.requireNonNull(eSlot);

		this.id = eSlot.id;
		this.name = Optional.ofNullable(eSlot.name)
				.filter(s -> !s.isBlank())
				.orElseThrow(() -> new IllegalArgumentException("Slot name must not be null or empty!)"));
	}

}
