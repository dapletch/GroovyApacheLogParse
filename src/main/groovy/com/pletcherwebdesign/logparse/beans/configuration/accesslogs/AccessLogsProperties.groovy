package com.pletcherwebdesign.logparse.beans.configuration.accesslogs

import groovy.transform.ToString

/**
 * Created by Seth on 9/23/2017.
 */
@ToString(includeFields = true, includeNames = true)
class AccessLogsProperties {

    String logFileDirectory
    String fileMatch
    File apacheAccessLogFile

    AccessLogsProperties(String logFileDirectory, String fileMatch, File apacheAccessLogFile) {
        this.logFileDirectory = logFileDirectory
        this.fileMatch = fileMatch
        this.apacheAccessLogFile = apacheAccessLogFile
    }
}
