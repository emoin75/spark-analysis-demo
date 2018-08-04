package com.customer.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Spring configuration class. Class annotated with JSR-330 or Spring annotation in the package (or subpackages) of the
 * package declared in basePackages will be scanned and all the beans will be made available via the Spring Context.
 */
@Configuration
@PropertySources({@PropertySource("classpath:customer-data-common.properties"),
        @PropertySource("classpath:environments/prod/customer-data-prod.properties")})
@Profile("prod")
public class CustomerDataProdPropertyConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }
}

