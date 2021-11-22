package cz.test.routing.cache.model;

import java.util.List;
import java.util.Objects;

public class CountryBorders {

    private String cca3;
    private List<String> borders;

    public CountryBorders(String cca3, List<String> borders) {
        this.cca3 = cca3;
        this.borders = borders;
    }

    public CountryBorders() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CountryBorders that = (CountryBorders) o;
        return Objects.equals(cca3, that.cca3) && Objects.equals(borders, that.borders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cca3, borders);
    }
}
