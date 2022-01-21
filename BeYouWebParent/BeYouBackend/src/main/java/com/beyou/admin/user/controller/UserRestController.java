package com.beyou.admin.user.controller;

import com.beyou.admin.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


//user control class to expose the Web service method check to get email that will be consumed by a client, please use a form.
@RestController
public class UserRestController {
    @Autowired
    //reference to the user service class user service.
    private UserService service;

    @PostMapping("/users/check_email")
    //method for checking the email user
    public String checkDuplicateEmail(Integer id, String email){
        return service.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }

}
