package com.beyou.address;

import java.util.List;

import com.beyou.common.entity.Address;
import com.beyou.common.entity.Country;
import com.beyou.common.entity.Customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class AddressRepositoryTests {
    
    @Autowired
    private AddressRepository repo;

    @Test
    public void testAddNew(){
        Integer customerId = 5;
        Integer countryId = 234; //USA

        Address newAddress = new Address();
        newAddress.setCustomer(new Customer(customerId));
        newAddress.setCountry(new Country(countryId));
        newAddress.setFirstName("Prathmesh ");
        newAddress.setLastName("Kapse");
        newAddress.setPhoneNumber("8830610123");
        newAddress.setAddressLine1("Sri Krishna Nagar");
        newAddress.setAddressLine2("new BVM");
        newAddress.setCity("Washington");
        newAddress.setState("New York");
        newAddress.setPostalCode("111000");

        Address saveAddress = repo.save(newAddress);
    }

    @Test
    public void testfindByCustomer(){
        Integer customerId = 6;
        List<Address> listAddresses = repo.findByCustomer(new Customer(customerId));

        listAddresses.forEach(System.out :: println);
    }

    @Test
    public void testfindByIdAndCustomer(){
        Integer addressId = 1;
        Integer customerId = 6;

        Address address = repo.findByIdAndCustomer(addressId, customerId);

        System.out.println(address);
    }

    @Test
    public void testUpdate() {
        Integer addressId = 1;
        // String phoneNumber = "8793203265";

        Address address = repo.findById(addressId).get();
        address.setDefaultForShipping(true);

    }

    @Test
    public void testDelete(){
        Integer addressId = 2;
        Integer customerId = 6;

        repo.deleteByIdAndCustomer(addressId, customerId);
    }

    @Test 
    public void testSetDefaultAddress(){
        Integer addressId = 4;
        repo.setDefaultAddress(addressId);

        Address address = repo.findById(addressId).get();
    }

    @Test 
    public void testSetNonDefaultAddress(){
        Integer addressId = 4;
        Integer customerId = 5;

        repo.setNonDefaultForOthers(addressId, customerId);
    }

    @Test
    public void testGetDefault(){
        Integer customerId = 14;
        Address address = repo.findDefaultByCustomer(customerId);
        System.out.println(address);
    }
}
