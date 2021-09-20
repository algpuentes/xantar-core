package com.xantar.xantarcore.db;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_SLOTS", schema = "XANTAR")
public class ESlot {

	@Id
	int id;
	String name;

	ESlot() {}

}
