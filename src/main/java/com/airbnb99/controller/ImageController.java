package com.airbnb99.controller;


import com.airbnb99.entity.Images;
import com.airbnb99.entity.Property;
import com.airbnb99.entity.PropertyUser;
import com.airbnb99.repository.ImagesRepository;
import com.airbnb99.repository.PropertyRepository;
import com.airbnb99.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping ("/api/v1/images")

public class ImageController {
    private ImagesRepository imagesRepository;
    private PropertyRepository propertyRepository;
    private BucketService bucketService;

    public ImageController(BucketService bucketService, PropertyRepository
            propertyRepository, ImagesRepository imagesRepository) {
        this.bucketService = bucketService;
        this.propertyRepository = propertyRepository;
        this.imagesRepository = imagesRepository;
    }
    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}", consumes =
            MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file,
                                        @PathVariable String bucketName,
                                        @PathVariable long propertyId,
                                        @AuthenticationPrincipal PropertyUser user) {
        String imageUrl = bucketService.uploadFile(file,bucketName);
        Optional<Property> propertyOpt = propertyRepository.findById(propertyId);

        if (propertyOpt.isPresent()) {
            Property property = propertyRepository.findById(propertyId).get();
            Images img = new Images();
            img.setImageUrl(imageUrl);
            img.setProperty(property);
            img.setPropertyUser(user);
            Images savedImage = imagesRepository.save(img);
            return new ResponseEntity<>(savedImage, HttpStatus.OK);
        }

        return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/delete/file/{bucketName}/file/{fileName}")
    public ResponseEntity<?> deleteFile(@PathVariable String bucketName, @PathVariable
    String fileName) {
        String response = bucketService.deleteFile(bucketName, fileName);
        if (response.contains("successfully")) {
            return new ResponseEntity<>("File successfully deleted", HttpStatus.OK);
        } else {

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}