package com.beyou.order;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.beyou.checkout.CheckOutInfo;
import com.beyou.common.entity.Address;
import com.beyou.common.entity.CartItem;
import com.beyou.common.entity.Customer;
import com.beyou.common.entity.order.Order;
import com.beyou.common.entity.order.OrderDetail;
import com.beyou.common.entity.order.OrderStatus;
import com.beyou.common.entity.order.OrderTrack;
import com.beyou.common.entity.order.PaymentMethod;
import com.beyou.common.entity.product.Product;
import com.beyou.common.exception.OrderNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    public static final int ORDERS_PER_PAGE = 5;

    @Autowired
    private OrderRepository repo;

    public Order createOrder(Customer customer, Address address, List<CartItem> cartItems,
        PaymentMethod paymentMethod, CheckOutInfo checkOutInfo){

        Order newOrder = new Order();
        newOrder.setOrderTime(new Date());

        if(paymentMethod.equals(PaymentMethod.PAYPAL)){
            newOrder.setOrderStatus(OrderStatus.PAID);
        }
        else{
            newOrder.setOrderStatus(OrderStatus.NEW);
        }

        // newOrder.setOrderStatus(OrderStatus.NEW);
        newOrder.setCustomer(customer);
        newOrder.setProductCost(checkOutInfo.getProductCost());
        newOrder.setSubTotal(checkOutInfo.getProductTotal());
        newOrder.setShippingCost(checkOutInfo.getShippingCostTotal());
        newOrder.setTax(0.0f);
        newOrder.setTotal(checkOutInfo.getPaymentTotal());
        newOrder.setPaymentMethod(paymentMethod);
        newOrder.setDeliveryDate(checkOutInfo.getDeliverDate());
        newOrder.setDeliverDays(checkOutInfo.getDeliverDays());

        if(address == null){
            newOrder.copyAddressFromCustomer();
        }
        else{
            newOrder.copyShippingAddress(address);
        }

        Set<OrderDetail> orderDetails = newOrder.getOrderDetails();

        for(CartItem cartItem : cartItems){
            Product product = cartItem.getProduct();
            
            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrder(newOrder);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setUnitPrice(product.getDiscountPrice());
            orderDetail.setProductCost(product.getCost() * cartItem.getQuantity());
            orderDetail.setSubTotal(cartItem.getSubTotal());
            orderDetail.setShippingCost(cartItem.getShippingCost());

            orderDetails.add(orderDetail);
        }

        OrderTrack track = new OrderTrack();
        track.setOrder(newOrder);
        track.setStatus(OrderStatus.NEW);
        track.setNotes(OrderStatus.NEW.defaultDescription());
        track.setUpdatedTime(new Date());

        newOrder.getOrderTracks().add(track);
        
        return repo.save(newOrder);
    }
    
    public Page<Order> listForCustomerByPage(Customer customer, int pageNum, 
			String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, ORDERS_PER_PAGE, sort);
		
		if (keyword != null) {
			return repo.findAll(keyword, customer.getId(), pageable);
		}
		
		return repo.findAll(customer.getId(), pageable);
	}

    public Order getOrder(Integer id, Customer customer){
        return repo.findByIdAndCustomer(id, customer);
    }

    public void setOrderReturnRequested(OrderReturnRequest request, Customer customer) throws OrderNotFoundException{

        Order order = repo.findByIdAndCustomer(request.getOrderId(), customer);

        if(order ==  null){
            throw new OrderNotFoundException("Order ID "+ request.getOrderId() + " not found");
        }

        if(order.isReturnedRequested()) return;

        OrderTrack track = new OrderTrack();
        track.setOrder(order);
        track.setUpdatedTime(new Date());
        track.setStatus(OrderStatus.RETURN_REQUESTED);

        String notes = "Reason: " + request.getReason();
        if(!"".equals(request.getNote())){
            notes += ". " + request.getNote();
        }

        track.setNotes(notes);

        order.getOrderTracks().add(track);
        order.setOrderStatus(OrderStatus.RETURN_REQUESTED);

        repo.save(order);
    }
}
