package cz.test.routing.cache.client;

import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountrySourceConfiguration {

    @Bean
    public Decoder decoder() {
        return new GsonDecoder();
    }
}
