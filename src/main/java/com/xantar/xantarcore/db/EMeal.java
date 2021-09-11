package com.xantar.xantarcore.db;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MEALS", schema = "XANTAR")
public class EMeal {

	@Id
	UUID	id;
	String	name;
	String	description;
	byte[]	imageThumb;

	public EMeal() {}

}
