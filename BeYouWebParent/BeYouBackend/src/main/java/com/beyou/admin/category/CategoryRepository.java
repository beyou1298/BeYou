package com.beyou.admin.category;


import java.util.List;

import com.beyou.common.entity.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category,Integer> {
    @Query("SELECT c FROM Category c WHERE c.parent.id IS NULL")
    public List<Category> findRootCategories(Sort sort);

    @Query("SELECT c FROM Category c WHERE c.parent.id IS NULL")
    public Page<Category> findRootCategories(Pageable pageable);

    //used for searching keyword from db
    @Query("SELECT c FROM Category c WHERE c.name LIKE %?1%")
    public Page<Category> search(String keyword, Pageable pageable);

    public Long countById(Integer id);

    //checking name for uniqueness of category
    public Category findByName(String name);

    public Category findByAlias(String alias);

    //updating the enabling and disabling status of user in repository or database
    @Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1 ")
    @Modifying //Bcz in query there is an UPDATE statement
    public void updateEnabledStatus(Integer id, boolean enabled);
}
