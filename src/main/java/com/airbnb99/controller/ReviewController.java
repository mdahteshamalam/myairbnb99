package com.airbnb99.controller;

import com.airbnb99.dto.ReviewDto;
import com.airbnb99.entity.Property;
import com.airbnb99.entity.PropertyUser;
import com.airbnb99.entity.Review;
import com.airbnb99.repository.PropertyRepository;
import com.airbnb99.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<String>addReview(
            @PathVariable long propertyId,
            @RequestBody Review review,
            @AuthenticationPrincipal PropertyUser user

    ){


        Optional<Property> opProperty = propertyRepository.findById(propertyId);
        Property property = opProperty.get();

        Review r = reviewRepository.findReviewByUser(property, user);
        if (r!=null){
            return new ResponseEntity<>("You have  already added review",HttpStatus.BAD_REQUEST);
        }
        review.setProperty(property);
        review.setPropertyUser(user);

        reviewRepository.save(review);
        return new ResponseEntity<>("Review added successfully", HttpStatus.OK);
    }

    @GetMapping("/userReviews")
    public  ResponseEntity<List<Review>> getUserReviews(@AuthenticationPrincipal
                                                            PropertyUser user){
        List<Review> reviews = reviewRepository.findByPropertyUser(user);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
