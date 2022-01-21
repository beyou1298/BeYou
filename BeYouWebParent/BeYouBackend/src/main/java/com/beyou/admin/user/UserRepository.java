package com.beyou.admin.user;

import com.beyou.admin.paging.SearchRepository;
import com.beyou.common.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends SearchRepository<User,Integer> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    public Long countById(Integer id);

    //Query for searching and filtering using text entered
    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName,' ',"
        + "u.lastName) LIKE %?1%")
    public Page<User> findAll(String keyword , Pageable pageable);

    //updating the enabling and disabling status of user in repository or database
    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1 ")
    @Modifying //Bcz in query there is an UPDATE statement
    public void updateEnabledStatus(Integer id, boolean enabled);

}


