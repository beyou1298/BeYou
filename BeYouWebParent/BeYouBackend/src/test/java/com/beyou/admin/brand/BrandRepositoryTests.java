package com.beyou.admin.brand;

import com.beyou.common.entity.Brand;

import java.util.Optional;

import com.beyou.common.entity.Category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {
    
    @Autowired
    private BrandRepository repo;

    @Test
    public void testCreateBrand1(){
        Category laptops =  new Category(6);
        Brand acer = new Brand("Acer");
        acer.getCategories().add(laptops);

        Brand saveBrand =  repo.save(acer);
    }

    @Test
    public void testCreateBrand2(){
        Category cellphones =  new Category(4);
        Category tablets =  new Category(7);

        Brand apple = new Brand("Apple");
        apple.getCategories().add(cellphones);
        apple.getCategories().add(tablets);

        Brand saveBrand =  repo.save(apple);

    }

    @Test
    public void testCreateBrand3(){

        Brand samsung = new Brand("Samsung");

        samsung.getCategories().add(new Category(29)); // category memory
        samsung.getCategories().add(new Category(24)); // category internal hard drive

        Brand saveBrand =  repo.save(samsung);

    }

    @Test
    public void testfindAll(){

        Iterable<Brand> brands = repo.findAll();
        brands.forEach(System.out::println);

    }

    @Test
    public void testGetById(){

        Brand brand = repo.findById(1).get();

    }

    @Test
    public void testUpdateName(){
        String newName = "Samsung Electronics";
        Brand samsung = repo.findById(10).get();

        samsung.setName(newName);

        Brand saveBrand =  repo.save(samsung);
    }

    @Test
    public void testDelete(){
        Integer id = 9;
        repo.deleteById(id);

        Optional<Brand> result = repo.findById(id);
    }
    
}   
