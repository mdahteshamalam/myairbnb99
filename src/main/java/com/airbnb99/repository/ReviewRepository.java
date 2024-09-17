package com.airbnb99.repository;

import com.airbnb99.entity.Property;
import com.airbnb99.entity.PropertyUser;
import com.airbnb99.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query ("SELECT r FROM Review r WHERE r.property=:property AND r.propertyUser=:user")
    Review findReviewByUser (@Param("property") Property property, @Param("user") PropertyUser propertyUser);

    List<Review> findByPropertyUser (PropertyUser user);


}