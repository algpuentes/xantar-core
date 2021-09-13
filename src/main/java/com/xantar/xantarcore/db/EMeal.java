package com.xantar.xantarcore.db;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MEALS", schema = "XANTAR")
public class EMeal {

	@Id
	int	id;
	String	name;
	String	description;
	byte[]	imageThumb;

	@ManyToMany
	@JoinTable(name = "SLOTS", schema = "XANTAR",
	joinColumns = { @JoinColumn(name = "meal_id", referencedColumnName = "id") },
	inverseJoinColumns = { @JoinColumn(name = "slot_id", referencedColumnName = "id") })
	Set<ESlot> slots = new HashSet<ESlot>();

	EMeal() {}

}
