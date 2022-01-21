package com.beyou.shoppingcart;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import com.beyou.Utility;
import com.beyou.address.AddressService;
import com.beyou.common.entity.Address;
import com.beyou.common.entity.CartItem;
import com.beyou.common.entity.Customer;
import com.beyou.common.entity.ShippingRate;
import com.beyou.customer.CustomerService;
import com.beyou.shipping.ShippingRateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ShoppingCartController {
    
    @Autowired
    private ShoppingCartService cartService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ShippingRateService shipService;

    @GetMapping("/cart")
    public String viewCart(Model model, HttpServletRequest request){
        Customer customer = getAuthenticatedCustomer(request);
        List<CartItem> cartItems = cartService.listCartItem(customer);
        
        float estimatedTotal = 0.01F;

        for(CartItem item : cartItems){
            estimatedTotal += item.getSubTotal();
        }

        Address defaultAddress = addressService.getDefaultAddress(customer);
        ShippingRate shippingRate = null;
        boolean usePrimaryAddressDefault = false;

        if(defaultAddress != null ){
            shippingRate = shipService.getShippingRateForAddress(defaultAddress);
        }
        else{
            usePrimaryAddressDefault = true;
            shippingRate = shipService.getShippingRateForCustomer(customer);
        }

        model.addAttribute("usePrimaryAddressDefault", usePrimaryAddressDefault);
        model.addAttribute("shippingSupported", shippingRate != null);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("estimatedTotal", estimatedTotal);

        return "cart/shopping_cart";
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request){
        String email = Utility.getEmailOfAuthenticatedCustomer(request);

        return customerService.getCustomerByEmail(email);
    }
}
