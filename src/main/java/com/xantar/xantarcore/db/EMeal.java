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

	public void addSlot(ESlot eSlot) {
		this.slots.add(eSlot);
		eSlot.meals.add(this);
	}

	public void removeSlot(ESlot eSlot) {
		this.slots.remove(eSlot);
		eSlot.meals.remove(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.description, this.slots, this.imageThumb);
	}

	@Override
	public boolean equals(Object other) {
		return Optional.ofNullable(other)
				.filter(EMeal.class::isInstance)
				.map(EMeal.class::cast)
				.filter(object -> this.compareAttributes(object))
				.isPresent();
	}

	private boolean compareAttributes(EMeal eMeal) {
		return Objects.equals(this.id, eMeal.id)
				&& Objects.equals(this.name, eMeal.name)
				&& Objects.equals(this.description, eMeal.description)
				&& Objects.equals(this.slots, eMeal.slots)
				&& Objects.equals(this.imageThumb, eMeal.imageThumb);
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
