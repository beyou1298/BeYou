package com.beyou.security;

import com.beyou.security.oauth2.CustomerOAuth2UserService;
import com.beyou.security.oauth2.DatabaseLoginSuccessHandler;
import com.beyou.security.oauth2.OAuth2LoginSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



//@Configuration because this is a Spring configuration class
@Configuration
//for security, we need to use the annotation 
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomerOAuth2UserService oAuth2UserService;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginHandler;

    @Autowired
    private DatabaseLoginSuccessHandler databaseLoginHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/account_details", "/update_account_details", "/orders/**",
            "/cart", "/address_book/**", "/checkout", "/place_order", "/reviews/**", "/process_paypal_order",
            "/write_review/**","/post_review").authenticated()
            .anyRequest().permitAll()
            .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .successHandler(databaseLoginHandler)
                .permitAll()
            .and()
            .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2LoginHandler)
            .and()
            .logout().permitAll()
            .and()
            .rememberMe()
                .key("Abcdefghijklmnopqrstuvw_1234567890")
                .tokenValiditySeconds(14 * 24 * 60 * 60) //14 days 2 weekd cookies stay time
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**","/js/**","/webjars/**");
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomerUserDetailService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

}
