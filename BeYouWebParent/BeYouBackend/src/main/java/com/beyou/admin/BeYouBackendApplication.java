package com.beyou.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
/*@EntityScan here, with an array of package name
The first package is com.shopme.common.entity for the entity classes. And the second package name is for the user module com.shopme.admin.user*/
@EntityScan({"com.beyou.common.entity"})
public class BeYouBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeYouBackendApplication.class, args);
	}

}
