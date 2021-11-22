package cz.test.routing.cache.model;

import java.util.List;
import java.util.Objects;

public class Country {

    private String cca3;
    private List<String> borders;
    private String region;

    public Country(String cca3, List<String> borders, String region) {
        this.cca3 = cca3;
        this.borders = borders;
        this.region = region;
    }

    public Country() {
    }

    public String getCountry() {
        return cca3;
    }

    public void setCca3(String cca3) {
        this.cca3 = cca3;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country that = (Country) o;
        return Objects.equals(cca3, that.cca3) && Objects.equals(borders, that.borders) && Objects.equals(region, that.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cca3, borders, region);
    }

    @Override
    public String toString() {
        return "Country{" +
                "cca3='" + cca3 + '\'' +
                ", borders=" + borders +
                ", region='" + region + '\'' +
                '}';
    }
}