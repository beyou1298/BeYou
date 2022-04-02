package com.beyou.admin.product;

import java.util.Date;
import java.util.Optional;

import com.beyou.common.entity.Brand;
import com.beyou.common.entity.Category;
import com.beyou.common.entity.product.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {
    
    @Autowired
    private ProductRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    //Test Create Products
    @Test
    public void testCreateProduct(){

        Brand brand = entityManager.find(Brand.class, 37);
        Category category = entityManager.find(Category.class, 5);

        Product product = new Product();
        product.setName("Acer Predeator Triton series");
        product.setAlias("Acer_Predator_triton");
        product.setShortDescription("Short description for Acer preadtor Triton");
        product.setFullDescription("Full description for Acer preadtor Triton");

        product.setBrand(brand);
        product.setCategory(category);

        product.setPrice(678);
        product.setCost(600);
        product.setEnabled(true);
        product.setInStock(true);

        product.setCreatedTime(new Date());
        product .setUpdatedTime(new Date());

        Product savedProduct = repo.save(product);


    }

    //Test List all products
    @Test
    public void testListAllProducts(){
        Iterable<Product> iterableProducts = repo.findAll();

        iterableProducts.forEach(System.out::println);
    }
    
    //Test get a product
    @Test
    public void testGetProduct(){
        Integer id =2;
        Product product = repo.findById(id).get();
        System.out.println(product);
    }

    //Test for Updating a Product
    @Test
    public void testUpdateProduct(){
        Integer id = 1;
        Product product = repo.findById(id).get();
        product.setPrice(490);

        repo.save(product);

        Product updatedProduct = entityManager.find(Product.class, id);
    }

    //Test for Deleting a product
    @Test
    public void testDeleteProduct(){
        Integer id = 3;
        repo.deleteById(id);

        Optional<Product> result = repo.findById(id);

    }

    //for adding multiple images
    @Test
    public void testSaveProductWithImages(){
        Integer productId = 1;
        Product product =  repo.findById(productId).get();

        product.setMainImage("main image.jpg");
        product.addExtraImage("extra image1.png");
        product.addExtraImage("extra_image2.png");
        product.addExtraImage("extra-image3.png");

        Product saveProduct = repo.save(product);
    }

    //adding product details test method
    @Test
    public void testSaveProductWithDetails(){
        Integer productid = 1;
        Product product = repo.findById(productid).get();

        product.addDetail("Device Memory","128GB");
        product.addDetail("CPU Model","Snapdragon 865");
        product.addDetail("OS","Android 10");

        Product saveProduct = repo.save(product);
    }

    @Test
    public void testUdpateReviewCountAndAverageRating(){
        Integer productId = 2;
        repo.updateReviewCountAndAverageRating(productId);
    }
}
