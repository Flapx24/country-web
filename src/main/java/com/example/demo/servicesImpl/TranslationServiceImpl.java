package com.example.demo.servicesImpl;

import com.example.demo.services.TranslationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TranslationServiceImpl implements TranslationService {

    private Map<String, String> translationMap = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @EventListener(ApplicationReadyEvent.class)
    public void loadTranslations() {
        try {
            ClassPathResource resource = new ClassPathResource("i18n/countriesTranslation.json");
            translationMap = objectMapper.readValue(
                    resource.getInputStream(), new TypeReference<Map<String, String>>() {
                    });
            System.out.println("Translation map loaded with " + translationMap.size() + " entries");
        } catch (IOException e) {
            System.err.println("Error loading translation map: " + e.getMessage());
            translationMap = new HashMap<>();
        }
    }

    @Override
    public String translateCountryName(String spanishName) {
        if (spanishName == null || spanishName.trim().isEmpty()) {
            return spanishName;
        }

        String originalName = spanishName.trim();
        String lowerCaseName = originalName.toLowerCase();
        String englishName = translationMap.get(lowerCaseName);
        if (englishName != null) {
            System.out.println("Translation found: '" + lowerCaseName + "' -> '" + englishName + "'");
            return englishName;
        }

        for (Map.Entry<String, String> entry : translationMap.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(lowerCaseName)) {
                System.out.println("Translation found: '" + lowerCaseName + "' -> '"
                        + entry.getValue() + "'");
                return entry.getValue();
            }
        }

        System.out.println(
                "No translation found for: '" + lowerCaseName + "', using original name: '" + originalName + "'");
        return originalName;
    }
}
