package com.example.demo.service;

import com.example.demo.model.Country;
import java.util.List;

public interface CountryService {
    
    /**
     * Search for countries by name
     * @param name The name of the country to search for
     * @return List of countries that match the search criteria
     * @throws RuntimeException if there's an error calling the external API
     */
    List<Country> searchCountriesByName(String name);
    
    /**
     * Get detailed information for a specific country
     * @param name The exact name of the country
     * @return The country information or null if not found
     * @throws RuntimeException if there's an error calling the external API
     */
    Country getCountryByName(String name);
}
