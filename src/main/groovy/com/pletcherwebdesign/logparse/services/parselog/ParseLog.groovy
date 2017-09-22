package com.pletcherwebdesign.logparse.services.parselog

import com.pletcherwebdesign.logparse.beans.model.LogRecord
import com.pletcherwebdesign.logparse.utils.LogUtils
import groovy.transform.ToString
import org.joda.time.DateTime
import org.springframework.stereotype.Service

import java.util.regex.Pattern

/**
 * Created by Seth on 9/20/2017.
 */

@Service
@ToString(includeNames = true, includeFields = true)
class ParseLog {

    File logFile
    DateTime timeEntered

    // list to save log records
    def logRecords = []

    static def regexNoRemoteUser = "((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))(?![\\d])" + // IPv4 IP Address 1
            ".*?" + // Non-greedy match on filler
            "(\\[.*?\\])" + // Square Braces 1
            ".*?" + // Non-greedy match on filler
            "(\".*?\")" + // Double Quote String 1
            ".*?" + // Non-greedy match on filler
            "(\\d+)" + // Integer Number 1
            ".*?" + // Non-greedy match on filler
            "(\\d+)" // Integer Number 2

    static def regexRemoteUserIncluded = "((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))(?![\\d])" + // IPv4 IP Address 1
            ".*?" + // Non-greedy match on filler
            "((?:[a-z][a-z]+))" + // Word 1
            ".*?" + // Non-greedy match on filler
            "(\\[.*?\\])" + // Square Braces 1
            ".*?" + // Non-greedy match on filler
            "(\".*?\")" + // Double Quote String 1
            ".*?" + // Non-greedy match on filler
            "(\\d+)" + // Integer Number 1
            ".*?" + // Non-greedy match on filler
            "(\\d+)" // Integer Number 2

    def parseLogWriteToDb(ParseLog parseLog) {

    }

    static def addLogRecordsToList(ParseLog parseLog) {
        def p = Pattern.compile(regexNoRemoteUser, Pattern.CASE_INSENSITIVE | Pattern.DOTALL)
        // convert file to ArrayList then iterate over the list via a closure
        def lines = parseLog.logFile.readLines()
        lines.each { String line ->
            def logRecord = new LogRecord()
            def m = p.matcher(line)
            if (m.find()) {
                logRecord.ipAddress = m.group(1)
                logRecord.timeAccessed = LogUtils.formatLogDateToDateTime(m.group(2).substring(1, 27))
                logRecord.request = m.group(3)
                logRecord.statCode = m.group(4).toInteger()
                logRecord.bytesSent = m.group(5).toInteger()
                logRecord.timeEntered = parseLog.timeEntered
                logRecords.add(logRecord)
            } else {
                logRecord = logRecordRemoteUserIncluded(parseLog.timeEntered, line)
                if (logRecord) {
                    logRecords.add(logRecord)
                }
            }
        }
    }

    static def logRecordRemoteUserIncluded(DateTime timeEntered, String line) {
        def p = Pattern.compile(regexRemoteUserIncluded, Pattern.CASE_INSENSITIVE | Pattern.DOTALL)
        def m = p.matcher(line)
        if (m.find()) {
            def logRecord = new LogRecord()
            logRecord.ipAddress = m.group(1)
            logRecord.remoteUser = m.group(2)
            logRecord.timeAccessed = LogUtils.formatLogDateToDateTime(m.group(3).substring(1, 27))
            logRecord.request = m.group(4)
            logRecord.statCode = m.group(5).toInteger()
            logRecord.bytesSent = m.group(6).toInteger()
            logRecord.timeEntered = timeEntered
            return logRecord
        }
        return null
    }
}
