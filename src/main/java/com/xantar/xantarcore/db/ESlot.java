package com.xantar.xantarcore.db;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_SLOTS", schema = "XANTAR")
public class ESlot {

	@Id
	int id;
	String name;

	@ManyToMany(mappedBy = "slots")
	final Set<EMeal> meals = new HashSet<>();

	ESlot() {}

	public void addSlot(EMeal eMeal) {
		this.meals.add(eMeal);
		eMeal.slots.add(this);
	}

	public void removeSlot(EMeal eMeal) {
		this.meals.remove(eMeal);
		eMeal.slots.remove(this);
	}

	@Override
	public boolean equals(Object other) {
		return Optional.ofNullable(other)
				.filter(ESlot.class::isInstance)
				.map(ESlot.class::cast)
				.filter(object -> this.compareAttributes(object))
				.isPresent();
	}

	private boolean compareAttributes(ESlot eSlot) {
		return this.id == eSlot.id
				&& Objects.equals(this.name, eSlot.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name);
	}

}
