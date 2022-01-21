package com.beyou.admin.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.beyou.admin.user.export.*;
import com.beyou.admin.user.*;
import com.beyou.admin.AmazonS3Util;

import com.beyou.admin.paging.PagingAndSortingHelper;
import com.beyou.admin.paging.PagingAndSortingParam;
import com.beyou.common.entity.Role;
import com.beyou.common.entity.User;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

// This is a Spring MVC controller class. So we need to use the annotation @Controller
@Controller
public class UserController {

    private String defaultRedirectURL = "redirect:/users/page/1?sortField=firstName&sortDir=asc";

    @Autowired
    private UserService service;
    /*The first handler method is to handle the request to see the user listing page, as you can see in the home page.templates... index.html here.
    In the menu section, we have the hyperlink /users here. This is the URL of the user listing page
    So we write the first handler method, with the annotation @GetMapping because the request...will be in HTTP GET method. And this is the handled URL*/
    @GetMapping("/users")
    public String listFirstPage(){
        /*So we need to update the controller here... to call the method listAll() of the UserService class that returns a List....
        of User objects from the database And we need to have access to Spring MVC Model object here... model... 
        and here we call service.listAll() And it will return a list of users... 
        listUsers And we put this listUsers onto the model... addAttributehere with the name is listUsers... 
        and object is the List collection... and this listUsers object will be available in the view user*/

        return "redirect:/users/page/1?sortField=firstName&sortDir=asc";
    }

    //For Paging URL
    @GetMapping("/users/page/{pageNum}")
    public String listByPage(@PagingAndSortingParam(listName = "listUsers", moduleURL = "/users") PagingAndSortingHelper helper,
        @PathVariable(name = "pageNum") int pageNum){


        service.listByPage(pageNum, helper);

        return "users/users";
    }


    @GetMapping("/users/new")
    public String newUser(Model model){
        List<Role> listRoles= service.listRoles();

        User user=new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Create New User");

        return "users/user_form";
    }
    //And we need to use the @PostMapping annotation for handling HTTP POST request. And the URL is /users/save
    @PostMapping("/users/save")
    /* to map the values of the form fields to a User object on the model here...
    in Spring MVC...we just declare User object as the method parameter*/
    
                                            /*redirectAttributes here... because the after persisting the User object into the database, we return a redirect view
                                            So we need to use RedirectAttributes here... to add an attribute
                                            which is then available in the users list page So we use redirectAttributes*/
    public String saveUser(User user,RedirectAttributes redirectAttributes,
    @RequestParam("image") MultipartFile multipartFile) throws IOException{

        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhotos(fileName);

            User savedUser = service.save(user);

            String uploadDir="user-photos/" + savedUser.getId();

            AmazonS3Util.removeFolder(uploadDir);
            AmazonS3Util.uploadFile(uploadDir, fileName, multipartFile.getInputStream());
            
        }
        else{
            if(user.getPhotos().isEmpty()) user.setPhotos(null);
            service.save(user);
        }
        
        
        redirectAttributes.addFlashAttribute("message","The user has been saved successfully.");

        return getRedirectURLtoAffectedUser(user);

    }

    private String getRedirectURLtoAffectedUser(User user) {
        String firstPartOfEmail = user.getEmail().split("@")[0];
        return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" + firstPartOfEmail;
    }
    /*in spring MVC, we can map the value here in zero point by using just addition that variable.
    Yeah, we didn't name names and then variable name integer edition.
    And insist that we return to the logic of your name to the use of using this.*/
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name="id") Integer id, Model model,RedirectAttributes redirectAttributes){
        try{
            User user=service.get(id);
            List<Role> listRoles= service.listRoles();
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User (ID: "+id+")");
            model.addAttribute("listRoles", listRoles);
            
            return "users/user_form";
        }
        catch(UserNotFoundException ex){

            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return defaultRedirectURL;
        }
        

    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name="id") Integer id, Model model,RedirectAttributes redirectAttributes){
        try{
            service.delete(id);
            String userPhotosDir = "user-photos/" + id;
            AmazonS3Util.removeFolder(userPhotosDir);

            redirectAttributes.addFlashAttribute("message",
            "The user ID "+id+" has been deleted successfully");
        }
        catch(UserNotFoundException ex){

            redirectAttributes.addFlashAttribute("message",ex.getMessage());
           
        }
        return defaultRedirectURL;
    }

    //controller for enabling and disabling the user status
    @GetMapping("/users/{id}/enabled/{status}")
    public String updateUserEnabStatus(@PathVariable(name="id") Integer id ,
    @PathVariable(name="status") boolean enabled , RedirectAttributes redirectAttributes){
        service.updateUserEnabledStatus(id, enabled);
        String status=enabled ? "enabled" : "disabled";
        String message = "The user ID "+id+" has been "+status;
        redirectAttributes.addFlashAttribute("message",message);

        return defaultRedirectURL;
    }

    //standalone method for exporting data to csv
    @GetMapping("/users/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException{
        List<User> listUsers = service.listAll();
        UserCsvExporter exporter = new UserCsvExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException{
        List<User> listUsers = service.listAll();
        UserExcelExporter exporter = new UserExcelExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/users/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException{
        List<User> listUsers = service.listAll();
        
        UserPDFExporter exporter = new UserPDFExporter();
        exporter.export(listUsers, response);
    }
}
