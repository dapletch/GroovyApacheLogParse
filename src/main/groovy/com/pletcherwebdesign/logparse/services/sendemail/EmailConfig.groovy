package com.pletcherwebdesign.logparse.services.sendemail

import com.pletcherwebdesign.logparse.beans.configuration.email.SmtpProperties
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Component

/**
 * Created by Seth on 9/24/2017.
 */

@Component
@Configuration
@ComponentScan
class EmailConfig {

    private def log = LoggerFactory.getLogger(EmailConfig.class)

    SmtpProperties smtpProperties

    @Autowired
    EmailConfig(SmtpProperties smtpProperties) {
        this.smtpProperties = smtpProperties
    }

    @Bean
    JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl()
        log.info("SmtpProperties: " + smtpProperties.toString())

        javaMailSender.host = smtpProperties.host
        javaMailSender.port = smtpProperties.port.toInteger()
        javaMailSender.username = smtpProperties.username
        javaMailSender.password = smtpProperties.password

        def props = [
                'mail.smtp.auth' : smtpProperties.authentication,
                'mail.smtp.starttls.enable' : smtpProperties.startTlsEnable,
                'mail.transport.protocol' : smtpProperties.transportProtocol,
                'mail.debug' : smtpProperties.mailDebug
        ] as Properties

        javaMailSender.javaMailProperties = props
        return javaMailSender
    }
}
