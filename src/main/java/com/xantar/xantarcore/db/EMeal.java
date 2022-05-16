package com.xantar.xantarcore.db;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "MEALS", schema = "XANTAR")
public class EMeal {

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
	Set<ESlot> slots = new HashSet<ESlot>();

	EMeal() {}

	EMeal(Meal meal) {
		Objects.requireNonNull(meal);

		this.id = meal.id;
		this.name = meal.name;
		this.description = meal.description;
		this.slots = Optional.ofNullable(meal.slots)
				.map(list -> list.stream().map(slot -> new ESlot(slot)).collect(Collectors.toSet()))
				.orElse(new HashSet<ESlot>());
		this.imageThumb = meal.imageThumb;
	}

	private EMeal(EMealBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.slots = builder.slots;
		this.imageThumb = builder.imageThumb;
	}

	static EMealBuilder builder() {
		return new EMealBuilder();
	}

	void addSlot(ESlot eSlot) {
		this.slots.add(eSlot);
		eSlot.meals.add(this);
	}

	void removeSlot(ESlot eSlot) {
		eSlot.meals.remove(this);
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

	static class EMealBuilder {
		private Integer	id;
		private String	name;
		private String	description;
		private Set<ESlot> slots = new HashSet<>();
		private byte[] imageThumb;

		private EMealBuilder() {}

		EMeal build() {
			return new EMeal(this);
		}

		EMealBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		EMealBuilder withName(String name) {
			this.name = name;
			return this;
		}

		EMealBuilder withDescription(String description) {
			this.description = description;
			return this;
		}

		EMealBuilder withSlots(Set<ESlot> slots) {
			this.slots = slots;
			return this;
		}

		EMealBuilder withImageThumb(byte[] imageThumb) {
			this.imageThumb = imageThumb;
			return this;
		}

	}


}
