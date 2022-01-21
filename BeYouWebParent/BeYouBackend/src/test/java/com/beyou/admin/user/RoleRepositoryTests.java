package com.beyou.admin.user;

import java.util.List;

import com.beyou.common.entity.Role;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;



//the annotation @DataJpaTest - to leverage the Data JPA test of Spring Data JPA
@DataJpaTest

/*And we will run the unit test method against the real database, so we need to use this annotation
@AutoConfigureTestDatabase - because by default, Spring Data JPA will run the test against an in-memory database.
So if you want to test with the real database, we need to override the default configuration
replace = NONE. So it will run the test against the real database.*/
@AutoConfigureTestDatabase(replace=Replace.NONE)

// to tell Spring Data JPA test to commit the changes after running that test by using the @Rollback annotation here
@Rollback(false)

public class RoleRepositoryTests {

	//Tshe @Autowired annotation to let Spring framework inject an instance of the RoleRepository interface.
	@Autowired
	private RoleRepository repo;
	
	//The first test method with the @Test annotation that comes from the package org.junit.jupiter.api That means we are using JUnit 5
	@Test
	//And we are going to persist the first Role object with the name as Admin and description is Manage everything.
	public void testCreateFirstRole() {
		Role roleAdmin=new Role("Admin","Manage everything");
		// And this save() method returns a persistent Role object So we can assign the return value to a new Role object: Role savedRole - for the purpose of assertion
		Role saveRole=repo.save(roleAdmin);

	}
	//The second test method that will persist the rest 4 roles into the database: Salesperson, Editor, Shipper and Assistant
	@Test
	public void testCreateRestRole() {
		Role roleSalesPerson=new Role("SalesPerson","Manage Product pricen, customer, shipping, orders and sales reports");
		Role roleEditor=new Role("Editor","Manage categories, brands, products, articlesa and menus");
		Role roleShipper=new Role("Shipper","View products, View orders and update order status");
		Role roleAssistant=new Role("Assistant","Manage questions and reviews");
		repo.saveAll(List.of(roleSalesPerson,roleEditor,roleShipper,roleAssistant));

		//The of() method of the List interface it is a default method of the List interface will return an immutable List containing the specified objects elements

	}
}
