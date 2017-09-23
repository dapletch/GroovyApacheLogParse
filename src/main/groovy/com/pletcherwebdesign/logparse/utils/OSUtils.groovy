package com.pletcherwebdesign.logparse.utils

import org.apache.log4j.Logger

/**
 * Created by Seth on 9/23/2017.
 */
class OSUtils {

    static def OS = System.getProperty("os.name").toLowerCase()

    static def workingDirectory = System.getProperty("user.dir")

    static boolean isWindows() {
        return (OS.contains("win")).toBoolean()
    }

    static def logger = Logger.getLogger(OSUtils.class)

    static def getFilePathAboveCurrentOne() {
        Integer index
        if (isWindows()) {
            logger.info("Operating System: " + OS)
            index = workingDirectory.lastIndexOf("\\")
            return workingDirectory.substring(0, index) + "\\".toString()
        } else {
            // Will work other operating systems, such as Unix/Linux, Mac, and Solaris
            logger.info("Operating System: " + OS)
            return workingDirectory + "/".toString()
        }
    }
}
