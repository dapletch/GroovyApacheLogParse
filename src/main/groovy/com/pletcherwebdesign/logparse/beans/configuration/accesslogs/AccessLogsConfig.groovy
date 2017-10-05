package com.pletcherwebdesign.logparse.beans.configuration.accesslogs

import com.pletcherwebdesign.logparse.beans.configuration.email.MessageBody
import com.pletcherwebdesign.logparse.utils.LogUtils
import com.pletcherwebdesign.logparse.utils.OSUtils
import org.joda.time.DateTime
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
class AccessLogsConfig {

    private Environment env

    @Autowired
    AccessLogsConfig(Environment env) {
        this.env = env
    }

    @Bean
    def accessLogProperties() {
        def dateStr = LogUtils.formatDateTimeForLog(new DateTime())
        return new AccessLogsProperties(
                env.getProperty("logfile.directory"),
                "${env.getProperty("logfile.prefix")}.${dateStr}.txt",
                new File("${OSUtils.filePathAboveCurrentOne}ApacheAccessLog${dateStr}.txt")
        )
    }

    @Bean
    def messageBody() {
        return new MessageBody(env.getProperty("smtp.recipient"))
    }
}
