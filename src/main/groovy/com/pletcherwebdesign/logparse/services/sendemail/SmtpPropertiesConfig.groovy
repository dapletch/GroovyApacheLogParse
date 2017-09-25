package com.pletcherwebdesign.logparse.services.sendemail

import com.pletcherwebdesign.logparse.beans.configuration.email.SmtpProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

/**
 * Created by Seth on 9/24/2017.
 */

@Component
@ComponentScan
@PropertySource("classpath:application.properties")
class SmtpPropertiesConfig {

    private Environment env

    @Autowired
    SmtpPropertiesConfig(Environment env) {
        this.env = env
    }

    @Bean
    SmtpProperties smtpProperties() {
        return new SmtpProperties(
                env.getProperty("smtp.host"),
                env.getProperty("smtp.port"),
                env.getProperty("smtp.username"),
                env.getProperty("smtp.password"),
                env.getProperty("smtp.auth"),
                env.getProperty("smtp.starttls.enable"),
                env.getProperty("smtp.transport.protocol"),
                env.getProperty("smtp.debug")
        )
    }
}
