package com.beyou.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



//@Configuration because this is a Spring configuration class
@Configuration
//for security, we need to use the annotation 
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(){
        return new BeYouUserDetailsService();
    }
    /*Then we need to declare Spring bean for the password encoder here... in the Spring Security 
    configuration class. Use the @Bean annotation*/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //method that configures authentication provider.
    //authentication will be based on database looking user in database
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.authenticationProvider(authenticationProvider());
    }

    //Override/implement Methods here and choose to override the method: configure() with the parameter type is HttpSecurity
    //http.authorizeRequests().anyRequest().permitAll(); gives permission to everyone to use the website
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/states/list_by_countries/**").hasAnyAuthority("Admin","Salesperson")
            .antMatchers("/users/**", "/settings/**", "/countries/**", "/states/**").hasAuthority("Admin") //for denying the user to access the user page who is not admin
            .antMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin","Editor") //for denying the user to access the user page who is not admin

            .antMatchers("/products/new", "/products/delete/**").hasAnyAuthority("Admin", "Editor")

            .antMatchers("/products/edit/**", "/products/save", "/products/check_unique").hasAnyAuthority("Admin", "Editor","Salesperson")

            .antMatchers("/products", "/products/" , "/products/detail/**", "/products/page/**")
                .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
            
            .antMatchers("/products/**").hasAnyAuthority("Admin","Editor")

            .antMatchers("/products/detail/**", "/customers/detail/**").hasAnyAuthority("Admin","Salesperson","Editor","Assistant")

            .antMatchers("/orders","/orders/", "/orders/page/**", "/orders/detail/**").hasAnyAuthority("Admin","Salesperson","Shipper")

            .antMatchers("/customers/**","/orders/**", "/get_shipping_cost", "/reports/**").hasAnyAuthority("Admin","Salesperson")

            .antMatchers("/orders_shipper/update/**").hasAnyAuthority("Shipper")

            .antMatchers("/reviews/**").hasAnyAuthority("Admin", "Assistant")

            .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .permitAll()
            .and().logout().permitAll()
            .and()
                .rememberMe().key("Abcdefghijklmnopqrstuvw_1234567890")
                .tokenValiditySeconds(7 * 24 * 60 * 60);
                ;
            http.headers().frameOptions().sameOrigin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**","/js/**","/webjars/**");
    }
    
}
