package com.beyou;

import javax.servlet.http.HttpServletRequest;

import com.beyou.common.entity.Customer;
import com.beyou.customer.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControllerHelper {

    @Autowired
    private CustomerService customerService;
    
    public Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		return customerService.getCustomerByEmail(email);
	}
}
