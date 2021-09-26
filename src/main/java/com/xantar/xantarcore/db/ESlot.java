package com.xantar.xantarcore.db;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.xantar.xantarcore.models.Slot;

@Entity
@Table(name = "MASTER_SLOTS", schema = "XANTAR")
public class ESlot {

	@Id
	int id;
	String name;

	@ManyToMany(mappedBy = "slots")
	final Set<EMeal> meals = new HashSet<>();

	ESlot() {}

	public void addSlot(EMeal meal) {
		this.meals.add(meal);
		meal.slots.add(this);
	}

	public void removeSlot(EMeal meal) {
		this.meals.remove(meal);
		meal.slots.remove(this);
	}

	@Override
	public boolean equals(Object other) {
		return Optional.ofNullable(other)
				.filter(Slot.class::isInstance)
				.map(Slot.class::cast)
				.filter(object -> this.compareAttributes(object))
				.isPresent();
	}

	private boolean compareAttributes(Slot slot) {
		return this.id == slot.id
				&& Objects.equals(this.name, slot.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name);
	}

}
