package com.pletcherwebdesign.logparse.beans.configuration.geolitecity

import com.pletcherwebdesign.logparse.beans.configuration.email.MessageBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

/**
 * Created by Seth on 9/23/2017.
 */

@Component
@PropertySource("classpath:application.properties")
class GeoLiteCityConfig {

    Environment env

    @Autowired
    GeoLiteCityConfig(Environment env) {
        this.env = env
    }

    @Bean
    def getGeoLiteCityProperties() {
        return new GeoLiteCityProperties(
                env.getProperty("geo.lite.city.file.url"),
                env.getProperty("geo.lite.city.file.gz"),
                env.getProperty("geo.lite.city.file")
        )
    }

    @Bean
    def messageBody() {
        return new MessageBody(env.getProperty("smtp.recipient"))
    }
}
