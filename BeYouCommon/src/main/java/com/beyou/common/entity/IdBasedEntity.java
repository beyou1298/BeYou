package com.beyou.common.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class IdBasedEntity {
    	// For the ID field, need to use the @Id annotation here.
	/*
	 * @GeneratedValue annotationto tell Hibernate that and the values of this field
	 * will be generated automatically. With the strategy is GenerationType.IDENTITY
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
