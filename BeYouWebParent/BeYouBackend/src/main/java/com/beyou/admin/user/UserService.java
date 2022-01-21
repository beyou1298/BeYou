package com.beyou.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import com.beyou.admin.paging.PagingAndSortingHelper;
import com.beyou.common.entity.Role;
import com.beyou.common.entity.User;


// this is a kind of business class, so we use the @Service annotation of the Spring framework 
@Service
@Transactional
public class UserService {
    
    public static final int USERS_PER_PAGE=4;
    //@Autowired annotation, to let Spring framework inject an instance, an implementation of the UserRepository at runtime.
    //we need to have a reference to RoleRepository interface here... roleRepo
    @Autowired
    private UserRepository userRepo;

    //we need to have a reference to RoleRepository interface here... roleRepo
    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //method returns user object based on email
    public User getByEmail(String email){
        return userRepo.getUserByEmail(email);
    }

    //implement a method that will return a list of Role objects from the database.
    public List<User> listAll(){
        return (List<User>) userRepo.findAll(Sort.by("firstName").ascending());
    }
    //for Pagation
    public void listByPage(int pageNum, PagingAndSortingHelper helper){
        helper.listEntities(pageNum, USERS_PER_PAGE, userRepo); 
    }
    public List<Role> listRoles(){
        return (List<Role>) roleRepo.findAll();
    }

    public User save(User user) {
        boolean isUpdatingUser = (user.getId() != null);
        if(isUpdatingUser){
            User existingUser = userRepo.findById(user.getId()).get();

            if(user.getPassword().isEmpty()){
                user.setPassword(existingUser.getPassword());
            }
            else{
                encodePassword(user);
            }
        }
        else{
            encodePassword(user);
        }
        
        return userRepo.save(user);
    }

    //method use to update password or all details when we update details in update detail section
    public User updateAcoount(User userInForm){
        User userInDB = userRepo.findById(userInForm.getId()).get();

        if(!userInForm.getPassword().isEmpty()){
            userInDB.setPassword(userInForm.getPassword());
            encodePassword(userInDB);
        }

        if(userInForm.getPhotos() != null){
            userInDB.setPhotos(userInForm.getPhotos());
        }

        userInDB.setFirstName(userInForm.getFirstName());
        userInDB.setLastName(userInForm.getLastName());

        return userRepo.save(userInDB);
    }

    private void encodePassword(User user){
        String encodedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    //this class checks uniqueness of email during creation of new user
    public boolean isEmailUnique(Integer id, String email){
        User userByEmail=userRepo.getUserByEmail(email);

    /*I use my email is, you know, then we return to you in this method because no user is with an email,
    meaning that the email is unique in the would be unique in the database.*/
    if(userByEmail==null) return true;

    /*And we check for the case that the user is being added to Bowlin is creating new iqua to express in
    equal no body, no meaning that the form is in Neumont, otherwise it is not known.
    That means the user is being added to it.*/
    boolean isCreatingNew =(id==null);

    //So then we check it is creating new user here.
    if(isCreatingNew){
        if(userByEmail != null) return false;
    }
    else{
        if(userByEmail.getId() != id){
            return false;
        }
    }
        return true;
    }

    public User get(Integer id) throws UserNotFoundException {
        try{
            return userRepo.findById(id).get();
        }
        catch(NoSuchElementException ex){
            throw new UserNotFoundException("Could not find any user with ID "+id);
        }
        
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = userRepo.countById(id);
        if(countById == null || countById == 0){
            throw new UserNotFoundException("Could not find any user with ID "+id);
        }
        
        userRepo.deleteById(id);
    }

    //function for updating the enable disable status of user
    public void updateUserEnabledStatus(Integer id, boolean enabled){
        userRepo.updateEnabledStatus(id, enabled);
    }
}
