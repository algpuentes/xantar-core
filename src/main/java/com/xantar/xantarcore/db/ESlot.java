package com.xantar.xantarcore.db;

import java.util.HashSet;
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

	ESlot(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	ESlot(Slot slot) {
		this.id = slot.id;
		this.name = slot.name;
	}

	ESlot() {}

	void addMeal(EMeal eMeal) {
		eMeal.slots.add(this);
		this.meals.add(eMeal);
	}

	void removeMeal(EMeal eMeal) {
		this.meals.remove(eMeal);
		eMeal.slots.remove(this);
	}

	Slot toSlot() {
		return new Slot(this.id, this.name);
	}

}
