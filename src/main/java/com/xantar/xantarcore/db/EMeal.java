package com.xantar.xantarcore.db;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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

import com.xantar.xantarcore.models.Meal;

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

	EMeal(EMealBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.slots = builder.slots;
		this.imageThumb = builder.imageThumb;
	}

	public void addSlot(ESlot slot) {
		this.slots.add(slot);
		slot.meals.add(this);
	}

	public void removeSlot(ESlot tag) {
		this.slots.remove(tag);
		tag.meals.remove(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.description, this.slots, this.imageThumb);
	}

	@Override
	public boolean equals(Object other) {
		return Optional.ofNullable(other)
				.filter(Meal.class::isInstance)
				.map(Meal.class::cast)
				.filter(object -> this.compareAttributes(object))
				.isPresent();
	}

	private boolean compareAttributes(Meal meal) {
		return Objects.equals(this.id, meal.id)
				&& Objects.equals(this.name, meal.name)
				&& Objects.equals(this.description, meal.description)
				&& Objects.equals(this.slots, meal.slots)
				&& Objects.equals(this.imageThumb, meal.imageThumb);
	}

	public static class EMealBuilder {
		private Integer	id;
		private String	name;
		private String	description;
		private Set<ESlot> slots = new HashSet<>();
		private byte[] imageThumb;

		public EMealBuilder() {}

		public EMeal build() {
			return new EMeal(this);
		}

		public EMealBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public EMealBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public EMealBuilder withDescription(String description) {
			this.description = description;
			return this;
		}

		public EMealBuilder withSlots(Set<ESlot> slots) {
			this.slots = slots;
			return this;
		}

		public EMealBuilder withImageThumb(byte[] imageThumb) {
			this.imageThumb = imageThumb;
			return this;
		}

	}


}
