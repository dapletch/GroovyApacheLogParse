package com.pletcherwebdesign.logparse.beans.configuration.geolitecity

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component


/**
 * Created by Seth on 9/23/2017.
 */

@Component
@PropertySource("classpath:application.properties")
class GeoLiteCityConfig {

    private Environment env

    @Autowired
    GeoLiteCityConfig(Environment env) {
        this.env = env
    }

    @Bean
    GeoLiteCityProperties getGeoLiteCityProperties() {
        return new GeoLiteCityProperties(
                env.getProperty("geo.lite.city.file.url"),
                env.getProperty("geo.lite.city.file.gz"),
                env.getProperty("geo.lite.city.file")
        )
    }
}
