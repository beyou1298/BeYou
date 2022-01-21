package com.beyou.shoppingcart;

import java.util.List;

import com.beyou.common.entity.CartItem;
import com.beyou.common.entity.Customer;
import com.beyou.common.entity.product.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartItemRepositoryTests {
    
    @Autowired 
    private CartItemRepository repo;

    @Autowired 
    private TestEntityManager entityManager;

    @Test
    public void testSaveItem(){
        Integer customerId = 1;
        Integer productId = 1;

        Customer customer = entityManager.find(Customer.class, customerId);
        Product product = entityManager.find(Product.class, productId);

        CartItem newItem = new CartItem();
        newItem.setCustomer(customer);
        newItem.setProduct(product);
        newItem.setQuantity(1);

        CartItem savedItem = repo.save(newItem);
    }

    @Test
    public void testSave2Item(){
        Integer customerId = 10;
        Integer productId = 10;

        Customer customer = entityManager.find(Customer.class, customerId);
        Product product = entityManager.find(Product.class, productId);

        CartItem item1 = new CartItem();
        item1.setCustomer(customer);
        item1.setProduct(product);
        item1.setQuantity(2);

        CartItem item2 = new CartItem();
        item2.setCustomer(new Customer(customerId));
        item2.setProduct(new Product(8));
        item2.setQuantity(3);

        Iterable<CartItem> iterable = repo.saveAll(List.of(item1, item2));
        CartItem savedItem = repo.save(item1);
    }

    @Test
    public void testFindByCustomer(){
        Integer customerId = 10;
        List<CartItem> listItems = repo.findByCustomer(new Customer(customerId));

        listItems.forEach(System.out :: println);
    }

    @Test
    public void testFindByCustomerAndProduct(){
        Integer customerId = 1;
        Integer productId = 1;

        CartItem item = repo.findByCustomerAndProduct(new Customer(customerId), new Product(productId));

        System.out.println(item);
    }

    @Test
    public void testUpdateQuantity(){
        Integer customerId = 1;
        Integer productId = 1;
        Integer quantity = 4;

        repo.updateQuantity(quantity, customerId, productId);

        CartItem item = repo.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
    }

    @Test
    public void testdeleteByCustomerAndProduct(){
        Integer customerId = 10;
        Integer productId = 10;

        repo.deleteByCustomerAndProduct(customerId, productId);

        CartItem item = repo.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
    }
}
