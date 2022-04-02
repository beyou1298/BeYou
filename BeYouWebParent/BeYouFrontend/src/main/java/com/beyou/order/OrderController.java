package com.beyou.order;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.beyou.ControllerHelper;

import com.beyou.common.entity.Customer;
import com.beyou.common.entity.order.Order;
import com.beyou.common.entity.order.OrderDetail;
import com.beyou.common.entity.product.Product;
import com.beyou.review.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
	
	@Autowired
	private ControllerHelper controllerHelper;

	@Autowired
    private ReviewService reviewService;

   
	@GetMapping("/orders")
	public String listFirstPage(Model model, HttpServletRequest request) {
		return listOrderByPage(model, request, 1, "orderTime", "desc", null);
	}
	

    @GetMapping("/orders/page/{pageNum}")
	public String listOrderByPage(Model model, HttpServletRequest request,
		@PathVariable(name = "pageNum") int pageNum,
		String sortField, String sortDir, String orderKeyword) {
		
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		
		Page<Order> page = orderService.listForCustomerByPage(customer, pageNum, sortField, sortDir, orderKeyword);
		List<Order> listOrders = page.getContent();
		
		model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("orderKeyword", orderKeyword);
        model.addAttribute("moduleURL", "/orders");
		model.addAttribute("listOrders", listOrders);

		long startCount = (pageNum-1) * OrderService.ORDERS_PER_PAGE + 1;
		
        long endCount = startCount + OrderService.ORDERS_PER_PAGE - 1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }

		model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);

		return "orders/orders_customer";
	}

    
	@GetMapping("/orders/detail/{id}")
	public String viewOrderDetail(Model model, @PathVariable(name = "id") Integer id,HttpServletRequest request){
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);

		Order order = orderService.getOrder(id, customer);

		setProductReviewableStatus(customer, order);

		model.addAttribute("order", order);

		return "orders/order_details_modal";
	}

	private void setProductReviewableStatus(Customer customer, Order order) {
		Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();
		
		while(iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			Product product = orderDetail.getProduct();
			Integer productId = product.getId();
			
			boolean didCustomerReviewProduct = reviewService.didCustomerReviewProduct(customer, productId);
			product.setReviewedByCustomer(didCustomerReviewProduct);
			
			if (!didCustomerReviewProduct) {
				boolean canCustomerReviewProduct = reviewService.canCustomerReviewProduct(customer, productId);
				product.setCustomerCanReview(canCustomerReviewProduct);
			}
			
		}
	}

}
