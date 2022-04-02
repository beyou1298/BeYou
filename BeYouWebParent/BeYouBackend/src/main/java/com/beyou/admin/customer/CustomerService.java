package com.beyou.admin.customer;


import java.util.List;
import java.util.NoSuchElementException;

import com.beyou.admin.paging.PagingAndSortingHelper;
import com.beyou.admin.setting.country.CountryRepository;
import com.beyou.common.entity.Country;
import com.beyou.common.entity.Customer;
import com.beyou.common.exception.CustomerNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

@Service
@Transactional
public class CustomerService {

    public static final int CUSTOMERS_PER_PAGE = 10;
    
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private CountryRepository countryRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void listByPage(int pageNum, PagingAndSortingHelper helper){
        helper.listEntities(pageNum, CUSTOMERS_PER_PAGE, customerRepo);
    }

    public void updateCustomerEnabledStatus(Integer id, boolean enabled){
        customerRepo.updateEnabledStatus(id, enabled);
    }

    public Customer get(Integer id) throws CustomerNotFoundException {
        try {
            return customerRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new CustomerNotFoundException("Could not find any customer with ID"+ id);        }
    }

    public List<Country> listAllCountries(){
        return countryRepo.findAllByOrderByNameAsc();
    }

    public boolean isEmailUnique(Integer id, String email){
        Customer existCustomer = customerRepo.findByEmail(email);

        if(existCustomer != null && existCustomer.getId() != id){
            return false;
        }
        
        return true;
    }

    public void save(Customer customerInForm){

        Customer customerInDb = customerRepo.findById(customerInForm.getId()).get();

        if(!customerInForm.getPassword().isEmpty()){
           String encodedPassword = passwordEncoder.encode(customerInForm.getPassword());
           customerInForm.setPassword(encodedPassword);
        }
        else{
            
            customerInForm.setPassword(customerInDb.getPassword());
        }
        customerInForm.setEnabled(customerInDb.isEnabled());
        customerInForm.setCreatedTime(customerInDb.getCreatedTime());
        customerInForm.setVerificationCode(customerInDb.getVerificationCode());
        customerInForm.setAuthenticationType(customerInDb.getAuthenticationType());
        customerInForm.setResetPasswordToken(customerInDb.getResetPasswordToken());
        
        customerRepo.save(customerInForm);
    }

    public void delete(Integer id) throws CustomerNotFoundException{
        Long count = customerRepo.countById(id);
        if(count == null || count == 0){
            throw new CustomerNotFoundException("Could not find any customers with ID "+id);
        }

        customerRepo.deleteById(id);
    }

    public List<Customer> listAll(){
        return (List<Customer>) customerRepo.findAll(Sort.by("firstName").ascending());
    }
}
