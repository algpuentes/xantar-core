package com.xantar.xantarcore.db;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.xantar.xantarcore.meals.Meal;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "MEALS", schema = "XANTAR")
class EMeal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer	id;
	String	name;
	String	description;
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	byte[]	imageThumb;

	@ManyToMany
	@JoinTable(name = "SLOTS", schema = "XANTAR",
	joinColumns = { @JoinColumn(name = "meal_id", referencedColumnName = "id") },
	inverseJoinColumns = { @JoinColumn(name = "slot_id", referencedColumnName = "id") })
	Set<ESlot> slots = new HashSet<>();

	EMeal() {}

	EMeal(Meal meal) {
		Objects.requireNonNull(meal);

		this.id = meal.id();
		this.name = meal.name();
		this.description = meal.description();
		this.slots = Optional.ofNullable(meal.slots())
				.map(list -> list.stream().map(ESlot::new).collect(Collectors.toSet()))
				.orElse(new HashSet<>());
		this.imageThumb = meal.imageThumb();
	}

	void addSlot(ESlot eSlot) {
		this.slots.add(eSlot);
	}

	void removeSlot(ESlot eSlot) {
		this.slots.remove(eSlot);
	}

	Meal toMeal() {
		return Meal.builder()
				.withId(this.id)
				.withName(this.name)
				.withDescription(this.description)
				.withImageThumb(this.imageThumb)
				.withSlots(this.slots.stream().map(ESlot::toSlot).collect(Collectors.toList()))
				.build();
	}

	@Override
	public boolean equals(Object o) {
		return Optional.ofNullable(o)
				.filter(other -> getClass() == other.getClass())
				.map(other -> (EMeal) other)
				.filter(this::compareAttributes)
				.isPresent();
	}

	private boolean compareAttributes(EMeal eMeal) {
		return Objects.equals(id, eMeal.id) && Objects.equals(name, eMeal.name) && Objects.equals(description, eMeal.description) && Arrays.equals(imageThumb, eMeal.imageThumb) && Objects.equals(slots, eMeal.slots);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(id, name, description, slots);
		result = 31 * result + Arrays.hashCode(imageThumb);
		return result;
	}
}
