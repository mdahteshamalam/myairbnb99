package com.airbnb99.repository;

import com.airbnb99.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;



public interface PropertyRepository extends JpaRepository<Property, Long> {
//   @Query ("SELECT p FROM Property p JOIN Location l on p.location = l.id  WHERE l.locationName = :locationName ")
//   List<Property> findPropertyByLocation (@Param("locationName") String locationName);
    @Query ("SELECT p FROM Property p JOIN Location l on p.location =l.id  JOIN Country c on p.country = c.id WHERE l.locationName = :locationName or c.countryName =:locationName ")
            List<Property> findPropertyByLocation (@Param("locationName") String locationName);
}