package com.beyou.checkout.paypal;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class PayPalApiTests {
    private static final String BASE_URL = "https://api.sandbox.paypal.com";
    private static final String GET_ORDER_API = "/v2/checkout/orders/";
    private static final String CLIENT_ID = "AdX_KmA7-O0sIoMBQY2CLntCr3LKkUVl6ZJnNvPwmA65rlRYGoK2E8aUWzZrPc6u2bUnUQCtHx7q8ZWM";
    private static final String CLIENT_SECRET = "EN2w5PwyiSbTNbBJe7ZFgf1NS8yZrJfB99KzoF8uJXJgwjVl9NC7Ecm8ZcoT6CJSC-Pwheo49PPlKIRV";

    @Test
    public void testGetOrderDetails(){
        String orderId = "4Y8463498P3781911";
        String requestURL = BASE_URL + GET_ORDER_API + orderId;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Accept-Language", "en_US");
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PayPalOrderResponse> response = restTemplate.exchange(requestURL, HttpMethod.GET, request, PayPalOrderResponse.class);

        PayPalOrderResponse orderResponse = response.getBody();

        System.out.println("ORDER ID" +orderResponse.getId());
        System.out.println("VALIDATE : "+orderResponse.validate(orderId));
    }
    

}
