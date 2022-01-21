package com.beyou.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;

// @Entity annotation from JPA to indicate that this class is an entity class.
@Entity
/*
 * To map with the table in the database. We need to use the @Table annotation
 * here, with the name equal to the actual name of the table in the database:
 * roles
 */
@Table(name = "roles")
public class Role extends IdBasedEntity{


	//for the field name, we need to use the annotation @Column. This field name has same name with the column in the database.
	@Column(length = 40, nullable = false, unique = true)
	private String name;

	@Column(length = 150, nullable = false)
	private String description;

	public Role() {

	}

	public Role(Integer id) {
		this.id = id;
	}

	public Role(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Role(String name) {
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
