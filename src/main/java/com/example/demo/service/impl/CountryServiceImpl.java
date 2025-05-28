package com.example.demo.service.impl;

import com.example.demo.model.Country;
import com.example.demo.service.CountryService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private static final String BASE_URL = "https://restcountries.com/v3.1";
    private static final String SEARCH_BY_NAME_URL = BASE_URL + "/name/{name}";

    private final RestTemplate restTemplate;

    public CountryServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Country> searchCountriesByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            Country[] countries = restTemplate.getForObject(
                    SEARCH_BY_NAME_URL,
                    Country[].class,
                    name.trim());

            return countries != null ? Arrays.asList(countries) : Collections.emptyList();

        } catch (HttpClientErrorException.NotFound e) {
            return Collections.emptyList();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error searching for country '" + name + "': " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            throw new RuntimeException("Network error while searching for country '" + name + "': " + e.getMessage(),
                    e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while searching for country '" + name + "': " + e.getMessage(),
                    e);
        }
    }

    @Override
    public Country getCountryByName(String name) {
        List<Country> countries = searchCountriesByName(name);

        if (countries.isEmpty()) {
            return null;
        }

        return countries.stream()
                .filter(country -> country.getName() != null &&
                        (name.equalsIgnoreCase(country.getName().getCommon()) ||
                                name.equalsIgnoreCase(country.getName().getOfficial())))
                .findFirst()
                .orElse(countries.get(0));
    }
}
