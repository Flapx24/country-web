package com.example.demo.servicesImpl;

import com.example.demo.model.Country;
import com.example.demo.services.CountryService;

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
    private static final String SEARCH_BY_CAPITAL_URL = BASE_URL + "/capital/{capital}";
    private static final String SEARCH_BY_REGION_URL = BASE_URL + "/region/{region}";

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
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        try {
            Country[] countries = restTemplate.getForObject(
                    SEARCH_BY_NAME_URL,
                    Country[].class,
                    name.trim());

            if (countries != null && countries.length > 0) {
                return countries[0];
            }
            return null;

        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error getting country '" + name + "': " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            throw new RuntimeException("Network error while getting country '" + name + "': " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while getting country '" + name + "': " + e.getMessage(), e);
        }
    }

    @Override
    public List<Country> searchCountriesByCapital(String capital) {
        if (capital == null || capital.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            Country[] countries = restTemplate.getForObject(
                    SEARCH_BY_CAPITAL_URL,
                    Country[].class,
                    capital.trim());

            return countries != null ? Arrays.asList(countries) : Collections.emptyList();

        } catch (HttpClientErrorException.NotFound e) {
            return Collections.emptyList();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error searching by capital '" + capital + "': " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            throw new RuntimeException("Network error while searching by capital '" + capital + "': " + e.getMessage(),
                    e);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Unexpected error while searching by capital '" + capital + "': " + e.getMessage(), e);
        }
    }

    @Override
    public List<Country> searchCountriesByRegion(String region) {
        if (region == null || region.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            Country[] countries = restTemplate.getForObject(
                    SEARCH_BY_REGION_URL,
                    Country[].class,
                    region.trim());

            return countries != null ? Arrays.asList(countries) : Collections.emptyList();

        } catch (HttpClientErrorException.NotFound e) {
            return Collections.emptyList();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error searching by region '" + region + "': " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            throw new RuntimeException("Network error while searching by region '" + region + "': " + e.getMessage(),
                    e);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Unexpected error while searching by region '" + region + "': " + e.getMessage(), e);
        }
    }
}
