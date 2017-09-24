package com.pletcherwebdesign.logparse.services.accesslogs

import com.pletcherwebdesign.logparse.beans.configuration.accesslogs.AccessLogsConfig
import com.pletcherwebdesign.logparse.beans.configuration.accesslogs.AccessLogsProperties
import com.pletcherwebdesign.logparse.utils.LogUtils
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.stereotype.Service

/**
 * Created by Seth on 9/23/2017.
 */

@Service
class ConcatenateAccessLogs {

    static def log = LoggerFactory.getLogger(ConcatenateAccessLogs.class)
    static def context = new AnnotationConfigApplicationContext(AccessLogsConfig.class)
    static def accessLogs = context.getBean(AccessLogsProperties.class)

    static def concatenateAccessLogs() {
        try {
            def apacheAccessLog = new File("${accessLogs.apacheAccessLogFile}")
            new File("${accessLogs.logFileDirectory}").eachFileMatch("${accessLogs.fileMatch}") { file ->
                apacheAccessLog << file.text
            }
            return LogUtils.isFileValid(apacheAccessLog)
        } catch (Exception e) {
            log.error("There was a problem with creating the log file to be parsed: ", e)
            return false
        }
    }
}
