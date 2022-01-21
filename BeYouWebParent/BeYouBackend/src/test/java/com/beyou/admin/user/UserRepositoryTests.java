package com.beyou.admin.user;


import com.beyou.common.entity.Role;
import com.beyou.common.entity.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;


import java.util.List;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)

public class UserRepositoryTests {
    @Autowired
    //we have a reference to the UserRepository... UserRepository repo here
	private UserRepository repo;

    /*And then, we need to set a role for this user. userNamHM.addRole() here. And we need to get a Role object from the database
    So with Spring Data JPA Test, we can declare an... a TestEntityManager here... private TestEntityManager
    this class is provided by Spring Data JPA for unit testing with repository*/

    @Autowired
    private TestEntityManager entityManager;
	
	@Test
    public void testCreateNewUserWithOneRole(){
        //We will use the entityManager to get a specific role from the database Use the method find() with the class type is Role.class
        Role roleAdmin = entityManager.find(Role.class,1);
        User usergc=new User("gaurav42stark@gmail.com", "gaurav42", "gaurav", "Chachada");
        usergc.addRole(roleAdmin);

        User savedUser = repo.save(usergc);

    }

    // the second test method that will persist a User object with 2 roles
    @Test
    public void testCreateNewUserWithTwoRole(){
        User userPk=new User("prathmesh.kapse@gmail.com","password","Prathmesh","Kapse");
        Role roleEditor=new Role(3);
        Role roleAssistandt=new Role(5);

        userPk.addRole(roleEditor);
        userPk.addRole(roleAssistandt);

        User savedUser = repo.save(userPk);
    }

    //test method for retrieving all users from the database.
    @Test
    public void testListAllUsers(){
        //findAll() here... and it returns a List collection
        Iterable<User> listUsers = repo.findAll();
        //Java Lambda Syntax in this collection, we just..print out the details of each user
        listUsers.forEach(user -> System.out.println(user));
    }

    
    @Test
    public void testGetUserById(){
        /*We use the findById() method of the UserRepository interface here
        because the findById() method returns an Optional of type User so here we need to get()*/
        User usernam=repo.findById(1).get();
        System.out.println(usernam);
    }

    @Test
    public void testUpdateUserDetails(){
        User usernam=repo.findById(1).get();
        //And for this user, I want to set the enabled status to true... because by default it is false
        usernam.setEnabled(true);
        usernam.setEmail("tonyjavaprogrammer@gmail.com");

        repo.save(usernam);

    } 
    @Test
    public void testUpdateUserRoles(){
        
        User usergc=repo.findById(1).get();
        Role RoleEditor = new Role(3);
        Role roleSalesPerson = new Role(2);

        usergc.getRoles().remove(RoleEditor);
        usergc.addRole(roleSalesPerson);

        repo.save(usergc);
    }

    @Test
    public void testDeleteUser(){
        Integer userid=2;
        repo.deleteById(userid);
    }

    @Test
    public void testGetUserByEmail(){
        String email="abcdefh@yahoo.com";
        User user=repo.getUserByEmail(email);
    }

    @Test
    public void testCountById(){
        Integer id=1;
        Long countById=repo.countById(id);
    }

    @Test
    public void testDisableUser(){
        Integer id =4;
        repo.updateEnabledStatus(id,false);

    }

    @Test
    public void testEnableUser(){
        Integer id =4;
        repo.updateEnabledStatus(id,true);

    }

    @Test
    public void testListFirstPage(){
        int pageNumber=1;
        int pageSize=4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page=repo.findAll(pageable);

        List<User> listUsers = page.getContent();
        listUsers.forEach(user -> System.out.println(user));

    }

    @Test
    public void testSearchUsers(){
        int pageNumber=0;
        int pageSize=4;
        String keyword = "bruce";

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        
        Page<User> page=repo.findAll(keyword,pageable);

        List<User> listUsers = page.getContent();
        listUsers.forEach(user -> System.out.println(user));

    }



}
