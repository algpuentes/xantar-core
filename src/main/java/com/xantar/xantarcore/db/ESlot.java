package com.xantar.xantarcore.db;

import com.xantar.xantarcore.meals.Slot;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "MASTER_SLOTS", schema = "XANTAR")
class ESlot {

	@Id
	int id;
	String name;

	ESlot(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	ESlot(Slot slot) {
		this.id = slot.id();
		this.name = slot.name();
	}

	ESlot() {}

	Slot toSlot() {
		return new Slot(this.id, this.name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ESlot eSlot = (ESlot) o;
		return id == eSlot.id && Objects.equals(name, eSlot.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
