package com.pletcherwebdesign.logparse.utils

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

/**
 * Created by Seth on 7/24/2017.
 */
class LogUtils {

    static isFileValid(File file) {
        if (file.isFile() && file.exists() && !file.isDirectory()) {
            return true
        }
        return false
    }

    static DateTime formatLogDateToDateTime(String dateStr) {
        return DateTime.parse(dateStr, DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss Z")).toDateTime();
    }

    static formatDateTimeForLog(DateTime dateTime) {
        return DateTimeFormat.forPattern("yyyy-MM-dd").print(dateTime)
    }
}
