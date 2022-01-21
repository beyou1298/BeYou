package com.beyou.checkout;

import java.util.List;

import com.beyou.common.entity.CartItem;
import com.beyou.common.entity.ShippingRate;
import com.beyou.common.entity.product.Product;

import org.springframework.stereotype.Service;

@Service
public class CheckOutService {
    private static final int DIM_DIVISOR = 139;

    public CheckOutInfo prepareCheckOut(List<CartItem> cartItems, ShippingRate shippingRate){
        CheckOutInfo checkOutInfo = new CheckOutInfo();

        float productCost = calculateProductCost(cartItems);
        float productTotal = calculateProductTotal(cartItems);
        float shippingCostTotal = calculateShippingCost(cartItems, shippingRate);
        float paymentTotal = productTotal + shippingCostTotal;

        checkOutInfo.setProductCost(productCost);
        checkOutInfo.setPaymentTotal(paymentTotal);
        checkOutInfo.setProductTotal(productTotal);
        checkOutInfo.setShippingCostTotal(shippingCostTotal);
        
        checkOutInfo.setDeliverDays(shippingRate.getDays());
        checkOutInfo.setCodSupported(shippingRate.isCodSupported());
        

        return checkOutInfo;
    }

    private float calculateShippingCost(List<CartItem> cartItems, ShippingRate shippingRate) {
        float shippingCostTotal = 0.0f;

        for(CartItem item : cartItems){
            Product product = item.getProduct();
            float dimWeight = (product.getLength() * product.getWidth() * product.getHeight())/DIM_DIVISOR;
            float finalWeight = product.getWeight() > dimWeight ? product.getWeight() : dimWeight;
            float shippingCost = finalWeight * item.getQuantity() * shippingRate.getRate();

            item.setShippingCost(shippingCost);
            shippingCostTotal += shippingCost;
        }
        return shippingCostTotal;
    }

    private float calculateProductTotal(List<CartItem> cartItems) {
       float total = 0.0f;

        for(CartItem item : cartItems){
            total += item.getSubTotal();
        }
        return total;
    }

    private float calculateProductCost(List<CartItem> cartItems) {
        float cost = 0.0f;

        for(CartItem item : cartItems){
            cost += item.getQuantity() * item.getProduct().getCost();
        }
        return cost;
    }
}
