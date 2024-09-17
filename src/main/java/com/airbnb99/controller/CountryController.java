package com.airbnb99.controller;

import com.airbnb99.dto.CountryDto;
import com.airbnb99.entity.Country;
import com.airbnb99.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {
   private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }
    @PostMapping("/addCountry")
    public ResponseEntity<String> addCountry(@RequestBody CountryDto countryDto){
        Country country=  countryService.addCountry(countryDto);
        if (country!=null){
            return new ResponseEntity<>("Country added Successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went error", HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @DeleteMapping("/deleteCountry/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) {
        Optional<Country> country = countryService.deleteCountry(id);
        if (country.isPresent()) {
            return new ResponseEntity<>("Country has been deleted successfully",
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND);
    }
    @PutMapping("/updateCountry/{id}")
    public ResponseEntity<String> updateCountry(
            @PathVariable Long id,
            @RequestBody CountryDto countryDto) {
        Country updated = countryService.updateCountry(id, countryDto);
        if(updated != null) {
            return new ResponseEntity<>("Country successfully updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getAllCountries")
    public ResponseEntity<List<Country>> getAllCountries() {
        List<Country> countries = countryService.getAllCountries();
        if (countries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>( countries, HttpStatus.OK);

    }

}

