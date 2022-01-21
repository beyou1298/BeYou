package com.beyou.product;

import com.beyou.common.entity.product.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTests {

    @Autowired
    ProductRepository repo;

    @Test
    public void testFindByAlias(){
        String alias = "Something";
        Product product = repo.findByAlias(alias);
    }
    
}
