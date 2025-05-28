package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    
    @JsonProperty("name")
    private Name name;
    
    @JsonProperty("capital")
    private List<String> capital;
    
    @JsonProperty("region")
    private String region;
    
    @JsonProperty("subregion")
    private String subregion;
    
    @JsonProperty("population")
    private Long population;
    
    @JsonProperty("area")
    private Double area;
    
    @JsonProperty("languages")
    private Map<String, String> languages;
    
    @JsonProperty("currencies")
    private Map<String, Currency> currencies;
    
    @JsonProperty("flags")
    private Flag flags;
    
    @JsonProperty("coatOfArms")
    private CoatOfArms coatOfArms;
    
    @JsonProperty("timezones")
    private List<String> timezones;
    
    @JsonProperty("continents")
    private List<String> continents;
    
    // Constructors
    public Country() {}
    
    // Getters and Setters
    public Name getName() {
        return name;
    }
    
    public void setName(Name name) {
        this.name = name;
    }
    
    public List<String> getCapital() {
        return capital;
    }
    
    public void setCapital(List<String> capital) {
        this.capital = capital;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getSubregion() {
        return subregion;
    }
    
    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }
    
    public Long getPopulation() {
        return population;
    }
    
    public void setPopulation(Long population) {
        this.population = population;
    }
    
    public Double getArea() {
        return area;
    }
    
    public void setArea(Double area) {
        this.area = area;
    }
    
    public Map<String, String> getLanguages() {
        return languages;
    }
    
    public void setLanguages(Map<String, String> languages) {
        this.languages = languages;
    }
    
    public Map<String, Currency> getCurrencies() {
        return currencies;
    }
    
    public void setCurrencies(Map<String, Currency> currencies) {
        this.currencies = currencies;
    }
    
    public Flag getFlags() {
        return flags;
    }
    
    public void setFlags(Flag flags) {
        this.flags = flags;
    }
    
    public CoatOfArms getCoatOfArms() {
        return coatOfArms;
    }
    
    public void setCoatOfArms(CoatOfArms coatOfArms) {
        this.coatOfArms = coatOfArms;
    }
    
    public List<String> getTimezones() {
        return timezones;
    }
    
    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }
    
    public List<String> getContinents() {
        return continents;
    }
    
    public void setContinents(List<String> continents) {
        this.continents = continents;
    }
    
    // Helper methods
    public String getCapitalString() {
        return capital != null && !capital.isEmpty() ? String.join(", ", capital) : "N/A";
    }
    
    public String getLanguagesString() {
        if (languages != null && !languages.isEmpty()) {
            return String.join(", ", languages.values());
        }
        return "N/A";
    }
    
    public String getCurrenciesString() {
        if (currencies != null && !currencies.isEmpty()) {
            return currencies.values().stream()
                    .map(c -> c.getName() + " (" + c.getSymbol() + ")")
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("N/A");
        }
        return "N/A";
    }
    
    public String getContinentsString() {
        return continents != null && !continents.isEmpty() ? String.join(", ", continents) : "N/A";
    }
    
    // Inner classes for nested JSON objects
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Name {
        @JsonProperty("common")
        private String common;
        
        @JsonProperty("official")
        private String official;
        
        public String getCommon() {
            return common;
        }
        
        public void setCommon(String common) {
            this.common = common;
        }
        
        public String getOfficial() {
            return official;
        }
        
        public void setOfficial(String official) {
            this.official = official;
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Currency {
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("symbol")
        private String symbol;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getSymbol() {
            return symbol;
        }
        
        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Flag {
        @JsonProperty("png")
        private String png;
        
        @JsonProperty("svg")
        private String svg;
        
        @JsonProperty("alt")
        private String alt;
        
        public String getPng() {
            return png;
        }
        
        public void setPng(String png) {
            this.png = png;
        }
        
        public String getSvg() {
            return svg;
        }
        
        public void setSvg(String svg) {
            this.svg = svg;
        }
        
        public String getAlt() {
            return alt;
        }
        
        public void setAlt(String alt) {
            this.alt = alt;
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CoatOfArms {
        @JsonProperty("png")
        private String png;
        
        @JsonProperty("svg")
        private String svg;
        
        public String getPng() {
            return png;
        }
        
        public void setPng(String png) {
            this.png = png;
        }
        
        public String getSvg() {
            return svg;
        }
        
        public void setSvg(String svg) {
            this.svg = svg;
        }
    }
}
