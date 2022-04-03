package com.beyou.admin.customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.beyou.admin.paging.PagingAndSortingHelper;
import com.beyou.admin.paging.PagingAndSortingParam;
import com.beyou.common.entity.Country;
import com.beyou.common.entity.Customer;
import com.beyou.common.exception.CustomerNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerController {
	private String defaultRedirectURL = "redirect:/customers/page/1?sortField=firstName&sortDir=asc";
	
	@Autowired private CustomerService service;
	
	@GetMapping("/customers")
	public String listFirstPage(Model model) {
		return defaultRedirectURL;
	}

	@GetMapping("/customers/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listCustomers", moduleURL = "/customers") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {

		service.listByPage(pageNum, helper);
		
		return "customers/customers";
	}
	
	@GetMapping("/customers/{id}/enabled/{status}")
	public String updateCustomerEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateCustomerEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The Customer ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		
		return defaultRedirectURL;
	}	
	
	@GetMapping("/customers/detail/{id}")
	public String viewCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Customer customer = service.get(id);
			model.addAttribute("customer", customer);
			
			return "customers/customer_detail_modal";
		} catch (CustomerNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return defaultRedirectURL;		
		}
	}
	
	@GetMapping("/customers/edit/{id}")
	public String editCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Customer customer = service.get(id);
			List<Country> countries = service.listAllCountries();
			
			model.addAttribute("listCountries", countries);			
			model.addAttribute("customer", customer);
			model.addAttribute("pageTitle", String.format("Edit Customer (ID: %d)", id));
			
			return "customers/customer_form";
			
		} catch (CustomerNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return defaultRedirectURL;
		}
	}
	
	@PostMapping("/customers/save")
	public String saveCustomer(Customer customer, RedirectAttributes ra) {
		service.save(customer);
		ra.addFlashAttribute("message", "The customer ID " + customer.getId() + " has been updated successfully.");
		return defaultRedirectURL;
	}

	@GetMapping("/customers/delete/{id}")
	public String deleteCustomer(@PathVariable Integer id, RedirectAttributes ra) {
		try {
			service.delete(id);			
			ra.addFlashAttribute("message", "The customer ID " + id + " has been deleted successfully.");
			
		} catch (CustomerNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}
		
		return defaultRedirectURL;
	}

    //standalone method for exporting data to csv
    @GetMapping("/customers/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException{
        List<Customer> listCustomers = service.listAll();
        CustomerCsvExporter exporter = new CustomerCsvExporter();
        exporter.export(listCustomers, response);
    }

    @GetMapping("/customers/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException{
        List<Customer> listCustomers = service.listAll();
        CustomerExcelExporter exporter = new CustomerExcelExporter();
        exporter.export(listCustomers, response);
    }

    // @GetMapping("/customers/export/pdf")
    // public void exportToPDF(HttpServletResponse response) throws IOException{
    //     List<Customer> listCustomers = service.listAll();
        
    //     CustomerPDFExporter exporter = new CustomerPDFExporter();
    //     exporter.export(listCustomers, response);
    // }
}
