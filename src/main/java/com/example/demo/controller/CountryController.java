package com.example.demo.controller;

import com.example.demo.model.Country;
import com.example.demo.services.CountryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "search";
    }

    @PostMapping("/search")
    public String searchCountry(String countryName,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (countryName == null || countryName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, ingresa el nombre de un país");
            return "redirect:/search";
        }

        try {
            List<Country> countries = countryService.searchCountriesByName(countryName.trim());
            if (countries.isEmpty()) {
                model.addAttribute("error", "No se encontraron países con el nombre: " + countryName);
                return "search";
            }

            model.addAttribute("countries", countries);
            model.addAttribute("searchTerm", countryName);
            return "search";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al buscar el país: " + e.getMessage());
            return "search";
        }
    }

    @GetMapping("/country")
    public String getCountryDetails(String countryName, Model model) {

        try {
            Country country = countryService.getCountryByName(countryName);
            if (country == null) {
                model.addAttribute("error", "No se encontró información para el país: " + countryName);
                return "error";
            }
            model.addAttribute("country", country);
            return "country-details";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al obtener información del país: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/search/capital")
    public String searchByCapital(String capital, Model model) {
        if (capital == null || capital.trim().isEmpty()) {
            model.addAttribute("error", "Por favor, ingresa el nombre de una capital");
            return "search";
        }

        try {
            List<Country> countries = countryService.searchCountriesByCapital(capital.trim());
            if (countries.isEmpty()) {
                model.addAttribute("error", "No se encontraron países con la capital: " + capital);
                return "search";
            }

            model.addAttribute("countries", countries);
            model.addAttribute("searchTerm", capital);
            model.addAttribute("searchType", "capital");
            return "results";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al buscar por capital: " + e.getMessage());
            return "search";
        }
    }

    @GetMapping("/search/region")
    public String searchByRegion(String region, Model model) {
        if (region == null || region.trim().isEmpty()) {
            model.addAttribute("error", "Por favor, ingresa el nombre de una región");
            return "search";
        }

        try {
            List<Country> countries = countryService.searchCountriesByRegion(region.trim());
            if (countries.isEmpty()) {
                model.addAttribute("error", "No se encontraron países en la región: " + region);
                return "search";
            }

            model.addAttribute("countries", countries);
            model.addAttribute("searchTerm", region);
            model.addAttribute("searchType", "region");
            return "results";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al buscar por región: " + e.getMessage());
            return "search";
        }
    }
}
