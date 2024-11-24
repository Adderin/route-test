package cz.test.routing.cache.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Country {

    private String cca3;
    private List<String> borders;
    private String region;

    public Country(String cca3, List<String> borders, String region) {
        this.cca3 = cca3;
        this.borders = borders;
        this.region = region;
    }

    public String getCountry() {
        return cca3;
    }

}