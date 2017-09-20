package com.pletcherwebdesign.logparse.services.parselog

import com.pletcherwebdesign.logparse.beans.model.LogRecord
import groovy.transform.ToString
import org.joda.time.DateTime
import java.util.regex.Pattern

/**
 * Created by Seth on 9/20/2017.
 */

@ToString(includeNames = true, includeFields = true)
class ParseLog {

    File logFile
    DateTime timeEntered

    // list to save log records
    def logRecords = []

    def regexNoRemoteUser = "((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))(?![\\d])" + // IPv4 IP Address 1
            ".*?" + // Non-greedy match on filler
            "(\\[.*?\\])" + // Square Braces 1
            ".*?" + // Non-greedy match on filler
            "(\".*?\")" + // Double Quote String 1
            ".*?" + // Non-greedy match on filler
            "(\\d+)" + // Integer Number 1
            ".*?" + // Non-greedy match on filler
            "(\\d+)" // Integer Number 2

    def regexRemoteUserIncluded = "((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))(?![\\d])" + // IPv4 IP Address 1
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

    def addLogRecordsToList(ParseLog parseLog) {
        def p = Pattern.compile(regexNoRemoteUser, Pattern.CASE_INSENSITIVE | Pattern.DOTALL)
        // convert file to ArrayList then iterate over the list via a closure
        def lines = parseLog.logFile.readLines()
        lines.each { String line ->
            def logRecord = new LogRecord()
            def m = p.matcher(line)
            if (m.find()) {
                // TODO create a method in model that converts String to DateTime
                logRecord.ipAddress = m.group(1)
                logRecord.timeAccessed = m.group(2)
                logRecord.request = m.group(3)
                logRecord.statCode = m.group(4).toInteger()
                logRecord.bytesSent = m.group(5).toInteger()
                logRecord.timeEntered = parseLog.timeEntered
                logRecords.add(logRecord)
            } else {
                // TODO account for the string when the user is included
                logRecord
            }
        }
    }
}
