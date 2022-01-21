package com.beyou.security.oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beyou.common.entity.AuthenticationType;
import com.beyou.common.entity.Customer;
import com.beyou.customer.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private CustomerService customerService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        CustomerOAuth2User oAuth2User = (CustomerOAuth2User) authentication.getPrincipal();

        String name = oAuth2User.getName();
        String email = oAuth2User.getEmail();
        String countryCode = request.getLocale().getCountry();
        String clientName = oAuth2User.getClientName();
        
        AuthenticationType authenticationType = getAuthenticationType(clientName);

        Customer customer = customerService.getCustomerByEmail(email);

        if(customer == null){
            customerService.addCustomerUponOAuthLogin(name, email, countryCode, authenticationType);
        }
        else{
            oAuth2User.setFullName(customer.getFullName());
            customerService.updateAuthenticationType(customer, authenticationType);
        }
        super.onAuthenticationSuccess(request, response, authentication);

    }
    
    private AuthenticationType getAuthenticationType(String clientName){
        if(clientName.equals("Google")){
            return AuthenticationType.GOOGLE;
        }
        else if(clientName.equals("Facebook")){
            return AuthenticationType.FACEBOOK;
        }
        else{
            return AuthenticationType.DATABASE;
        }
    }
}
