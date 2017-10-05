package com.pletcherwebdesign.logparse.services.accesslogs

import com.pletcherwebdesign.logparse.beans.configuration.accesslogs.AccessLogsConfig
import com.pletcherwebdesign.logparse.beans.configuration.accesslogs.AccessLogsProperties
import com.pletcherwebdesign.logparse.beans.configuration.email.MessageBody
import com.pletcherwebdesign.logparse.services.sendemail.SendEmail
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
    static def message = context.getBean(MessageBody.class)

    // Recipient value is obtained via the context
    static def messageBody = new MessageBody(
            message.recipient,
            "A problem occurred while obtaining the log file",
            "There were no log files found to be parsed today: ${new Date()}"
    )

    static def concatenateAccessLogs() {
        try {
            def apacheAccessLog = new File("${accessLogs.apacheAccessLogFile}")
            new File("${accessLogs.logFileDirectory}").eachFileMatch("${accessLogs.fileMatch}") { file ->
                apacheAccessLog << file.text
            }
            // if the file is valid return if not send the send the email and return false
            return LogUtils.isFileValid(apacheAccessLog) ?: SendEmail.sendEmailNoAttachmentIncluded(messageBody)
        } catch (Exception e) {
            log.error("There was a problem with creating the log file to be parsed: ", e)
            SendEmail.sendEmailNoAttachmentIncluded(
                    new MessageBody(
                            messageBody.recipient,
                            messageBody.subject,
                            "An exception was thrown while obtaining the log files: \n ${e.toString()}"
                    )
            )
            return false
        }
    }
}
