package com.beyou.admin.user.controller;

import java.io.IOException;

import com.beyou.admin.FileUploadUtil;
import com.beyou.admin.security.BeYouUserDetails;
import com.beyou.common.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.beyou.admin.user.*;

@Controller
public class AccountController {
    
    @Autowired
    private UserService service;

    @GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal BeYouUserDetails loggedUser,Model model){
        String email = loggedUser.getUsername();
        User user = service.getByEmail(email);
        model.addAttribute("user", user);

        return "users/account_form";
    }

    @PostMapping("/account/update")
    /* to map the values of the form fields to a User object on the model here...
    in Spring MVC...we just declare User object as the method parameter*/
    
                                            /*redirectAttributes here... because the after persisting the User object into the database, we return a redirect view
                                            So we need to use RedirectAttributes here... to add an attribute
                                            which is then available in the users list page So we use redirectAttributes*/
    public String saveUser(User user,RedirectAttributes redirectAttributes,
    @AuthenticationPrincipal BeYouUserDetails loggedUser,
    @RequestParam("image") MultipartFile multipartFile) throws IOException{

        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhotos(fileName);

            User savedUser = service.updateAcoount(user);

            String uploadDir="user-photos/" + savedUser.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            
        }
        else{
            if(user.getPhotos().isEmpty()) user.setPhotos(null);
            service.updateAcoount(user);
        }
        
        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());
        
        redirectAttributes.addFlashAttribute("message","Your account details have been updated.");
        return "redirect:/account";
    }


}
