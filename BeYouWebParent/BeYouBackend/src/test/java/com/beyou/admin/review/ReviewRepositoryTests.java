package com.beyou.admin.review;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.beyou.common.entity.Customer;
import com.beyou.common.entity.Review;
import com.beyou.common.entity.product.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ReviewRepositoryTests {
    
    @Autowired
    private ReviewRepository repo;

    @Test
    public void testCreateReview(){
        Integer productId = 1;
        Product product = new Product(productId);

        Integer customerId = 2;
        Customer customer = new Customer(customerId);

        Review review = new Review();
        review.setProduct(product);
        review.setCustomer(customer);
        review.setReviewTime(new Date());
        // review.setHeadline("Perfect for my needs. Loving It");
        review.setHeadline("An awesome Camera");
        // review.setComment("Nice to have : wireless remote, GPS, IOS apps...... ");
        review.setComment("Overall Great Camera and is highly capable...");
        review.setRating(4.2);

        Review savedReview = repo.save(review);
    }

    @Test
    public void testListReviews(){
        List<Review> listReviews = repo.findAll();
        listReviews.forEach(System.out :: println);
    }

    @Test
    public void testGetReviews(){
        Integer id = 2;
        Review review = repo.findById(id).get();

        System.out.println(review);
    }

    @Test
    public void testUpdateReview(){
        Integer id = 1;
        String headline = "An awesome Camera";
        String comment = "Overall Great Camera and is highly capable...";

        Review review = repo.findById(id).get();
        review.setComment(comment);
        review.setHeadline(headline);

        Review updatedReview = repo.save(review);
    }

    @Test 
    public void testDeleteReview(){
        Integer id = 1;
        repo.deleteById(id);

        Optional<Review> findById = repo.findById(id);
    }
}
