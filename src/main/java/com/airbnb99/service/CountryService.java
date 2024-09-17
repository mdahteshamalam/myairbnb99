package com.airbnb99.service;

import com.airbnb99.dto.CountryDto;
import com.airbnb99.entity.Country;
import com.airbnb99.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country addCountry(CountryDto countryDto) {
        Country country = new Country();
        country.setCountryName(countryDto.getCountryName());
        Country save = countryRepository.save(country);
        return save;
    }


    public Optional<Country> deleteCountry(Long id) {
        Optional<Country> byId = countryRepository.findById(id);
        if (byId.isPresent()){
            countryRepository.deleteById(id);
        }
      
        return byId;
    }

    public Country updateCountry(Long id, CountryDto countryDto) {
        Optional<Country> country = countryRepository.findById(id);
        if(country.isPresent()) {
            Country contr = country.get();
            contr.setCountryName(countryDto.getCountryName());
            return countryRepository.save(contr);
        }
        return null;
    }

    public List<Country> getAllCountries() {
        List<Country> post =  countryRepository.findAll();
        return post;
    }
}
