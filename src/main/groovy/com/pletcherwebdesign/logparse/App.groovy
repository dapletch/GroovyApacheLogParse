package com.pletcherwebdesign.logparse

import com.pletcherwebdesign.logparse.utils.LogUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class App {

    static Logger log = LoggerFactory.getLogger(App.class)

    static void main(String[] args) {

        if (args.length > 0 && LogUtils.isLogFileValid(new File(args[0]))) {
            log.info("File is valid: " + args[0])
        } else {
            log.info("No file was inputted...")
        }
    }
}
