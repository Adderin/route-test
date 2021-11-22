package cz.test.routing.cache.client;

import cz.test.routing.cache.model.Country;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(value = "countrySourceClient", url = "${countries.url}", configuration = CountrySourceConfiguration.class)
public interface CountrySourceClient {

    @RequestMapping(
        value = "/mledoze/countries/master/countries.json",
        method = RequestMethod.GET
    )
    @ResponseBody
    List<Country> getCountryList();

}
