package com.beyou.admin.brand;

import java.util.List;

import com.beyou.admin.paging.SearchRepository;
import com.beyou.common.entity.Brand;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends SearchRepository<Brand,Integer> {

    public Long countById(Integer id);

    public Brand findByName(String name);

    //used for searching keyword from db
    @Query("SELECT b FROM Brand b WHERE b.name LIKE %?1%")
    public Page<Brand> findAll(String keyword, Pageable pageable);

    @Query("SELECT NEW Brand(b.id,b.name) FROM Brand b ORDER BY b.name ASC")
    public List<Brand> findAll();
}
