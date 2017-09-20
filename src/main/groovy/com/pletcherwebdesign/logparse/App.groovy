package com.pletcherwebdesign.logparse

import com.pletcherwebdesign.logparse.utils.LogUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class App implements CommandLineRunner {

    static Logger log = LoggerFactory.getLogger(App.class)

    static void main(String[] args) {
        SpringApplication.run(App.class)
    }

    @Override
    void run(String... args) throws Exception {
        if (args.length > 0) {
            log.info("Argument inputted: ", args)
        } else {
            log.error("No arguments inputted. Please try again...")
        }
    }
}
