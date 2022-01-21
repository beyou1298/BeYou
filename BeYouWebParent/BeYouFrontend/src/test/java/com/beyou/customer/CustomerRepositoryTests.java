package com.beyou.customer;

import java.util.Date;
import java.util.Optional;

import com.beyou.common.entity.AuthenticationType;
import com.beyou.common.entity.Country;
import com.beyou.common.entity.Customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {
    
    @Autowired
    private CustomerRepository repo;

    @Autowired
    private  TestEntityManager entityManager;

    @Test
    public void testCreateCustomer1(){
        Integer countryId = 2; //USA
        Country country = entityManager.find(Country.class, countryId);

        Customer customer = new Customer();
        customer.setCountry(country);
        customer.setFirstName("David");
        customer.setLastName("Fountaine");
        customer.setPassword("password123");
        customer.setEmail("david_123_fountaine@gmail.com");
        customer.setPhoneNumber("123-456-7891");
        customer.setAddressLine1("1927 West drave");
        customer.setCity("Saracamento");
        customer.setState("California");
        customer.setPostalCode("95867");
        customer.setCreatedTime(new Date());

        Customer savedCustomer = repo.save(customer);
    }

    @Test
    public void testListCustomer(){
        Iterable<Customer> customers = repo.findAll();
        customers.forEach(System.out :: println);

    }

    @Test
    public void testupdateCustomer(){
        Integer customerId = 4;
        String lastName = "Chachada";

        Customer customer = repo.findById(customerId).get();
        customer.setLastName(lastName);
        customer.setEnabled(true);

        Customer updatedCustomer = repo.save(customer);
    }

    @Test
    public void testGetCustomer(){
        Integer customerId = 1;
        Optional<Customer> findById = repo.findById(customerId);

        Customer customer = findById.get();
        System.out.println(customer);
    }

    @Test
    public void testDeleteCustomer(){
        Integer customerId = 4;
        repo.deleteById(customerId);

        Optional<Customer> findById = repo.findById(customerId);
    }

    @Test
    public void testfindByEmail(){
        String email = "david_123_fountaine@gmail.com";
        Customer customer = repo.findByEmail(email);

        System.out.println(customer);
    }

    @Test
    public void testfindByVerificationCode(){
        String code = "code_123";
        Customer customer = repo.findByVerifiationCode(code);

        System.out.println(customer);
    }

    @Test
    public void testEnabledCustomer(){
        Integer customerId = 4;
        repo.enable(customerId);

        Customer customer = repo.findById(customerId).get();
    }

    @Test
    public void testUpdateAuthenticationType(){
        Integer id = 1;
        repo.updateAuthenticationType(id, AuthenticationType.DATABASE);
    }
}
