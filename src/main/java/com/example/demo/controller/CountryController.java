package com.example.demo.controller;

import com.example.demo.model.Country;
import com.example.demo.service.CountryService;
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

    @GetMapping("/")
    public String home() {
        return "index";
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
            return "results";

        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al buscar el país: " + e.getMessage());
            return "search";
        }
    }

    @GetMapping("/country")
    public String getCountryDetails(String name, Model model) {

        try {
            Country country = countryService.getCountryByName(name);

            if (country == null) {
                model.addAttribute("error", "No se encontró información para el país: " + name);
                return "error";
            }

            model.addAttribute("country", country);
            return "country-details";

        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al obtener información del país: " + e.getMessage());
            return "error";
        }
    }
}
