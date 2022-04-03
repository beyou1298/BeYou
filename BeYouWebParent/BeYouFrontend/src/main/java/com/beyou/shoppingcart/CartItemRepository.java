package com.beyou.shoppingcart;

import java.util.List;

import com.beyou.common.entity.CartItem;
import com.beyou.common.entity.Customer;
import com.beyou.common.entity.product.Product;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
	public List<CartItem> findByCustomer(Customer customer);
	
	public CartItem findByCustomerAndProduct(Customer customer, Product product);
	
	@Modifying
	@Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.customer.id = ?2 AND c.product.id = ?3")
	public void updateQuantity(Integer quantity, Integer customerId, Integer productId);
	
	@Modifying
	@Query("DELETE FROM CartItem c WHERE c.customer.id = ?1 AND c.product.id = ?2")
	public void deleteByCustomerAndProduct(Integer customerId, Integer productId);
	
	@Modifying
	@Query("DELETE CartItem c WHERE c.customer.id = ?1")
	public void deleteByCustomer(Integer customerId);
}
