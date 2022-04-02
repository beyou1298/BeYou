package com.beyou.address;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.beyou.ControllerHelper;
import com.beyou.common.entity.Address;
import com.beyou.common.entity.Country;
import com.beyou.common.entity.Customer;
import com.beyou.customer.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AddressController {
    
    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerService customerService;

    @Autowired 
    private ControllerHelper controllerHelper;

    @GetMapping("/address_book")
    public String showAddressBook(Model model, HttpServletRequest request){
        Customer customer = controllerHelper.getAuthenticatedCustomer(request);
        List<Address> listAddresses = addressService.listAddressBook(customer);
        
        boolean usePrimaryAsDefaultAddress = true;
        for(Address address : listAddresses){
            if(address.isDefaultForShipping()){
                usePrimaryAsDefaultAddress = false;
                break;
            }
        }

        model.addAttribute("listAddresses", listAddresses);
        model.addAttribute("customer", customer);
        model.addAttribute("usePrimaryAsDefaultAddress", usePrimaryAsDefaultAddress);

        return "address_book/addresses";
    }
    

    @GetMapping("/address_book/new")
    public String newAddress(Model model){
        List<Country> listCountries = customerService.listAllCountries();

        model.addAttribute("listCountries", listCountries);
        model.addAttribute("address", new Address());
        model.addAttribute("pageTitle", "Add new Address");

        return "address_book/address_form";
    }

    @PostMapping("/address_book/save")
    public String saveAddress(Address address, HttpServletRequest request, RedirectAttributes ra){
        Customer customer = controllerHelper.getAuthenticatedCustomer(request);

        address.setCustomer(customer);
        addressService.save(address);

        String redirectOption = request.getParameter("redirect");
		String redirectURL = "redirect:/address_book";

        if("checkout".equals(redirectOption)){
            redirectURL += "?redirect=checkout";
        }

        ra.addFlashAttribute("message", "The address has been saved successfully");

        return redirectURL;
    }

    @GetMapping("/address_book/edit/{id}")
    public String editAddress(@PathVariable("id") Integer addressId, Model model,
        HttpServletRequest request){

        Customer customer = controllerHelper.getAuthenticatedCustomer(request);
        List<Country> listCountries = customerService.listAllCountries();

        Address address = addressService.get(addressId, customer.getId());

        model.addAttribute("listCountries", listCountries);
        model.addAttribute("address", address);
        model.addAttribute("pageTitle", "Edit Address (ID: "+ addressId + ")");

        return "address_book/address_form";
    }

    @GetMapping("/address_book/delete/{id}")
	public String deleteAddress(@PathVariable("id") Integer addressId, RedirectAttributes ra,
			HttpServletRequest request) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		addressService.delete(addressId, customer.getId());
		
		ra.addFlashAttribute("message", "The address ID " + addressId + " has been deleted.");
		
		return "redirect:/address_book";
	}

    @GetMapping("/address_book/default/{id}")
    public String setDefaultAddress(@PathVariable("id") Integer addressId,
        HttpServletRequest request){

        Customer customer = controllerHelper.getAuthenticatedCustomer(request);
        addressService.setDefaultAddress(addressId, customer.getId());

        String redirectOption = request.getParameter("redirect");
		String redirectURL = "redirect:/address_book";

		if("cart".equals(redirectOption)){
			redirectURL = "redirect:/cart";
		}
        else if("checkout".equals(redirectOption)){
            redirectURL = "redirect:/checkout";
        }

        return redirectURL;
    }
}
