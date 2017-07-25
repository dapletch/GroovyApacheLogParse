package com.pletcherwebdesign.logparse.utils

/**
 * Created by Seth on 7/24/2017.
 */
class LogUtils {

    static isLogFileValid(File file) {
        if (file.isFile() && file.exists() && !file.isDirectory()) {
            return true
        }
        return false
    }
}
