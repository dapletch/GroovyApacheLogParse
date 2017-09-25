package com.pletcherwebdesign.logparse.beans.configuration.email

import groovy.transform.ToString

/**
 * Created by Seth on 9/24/2017.
 */

@ToString(includeFields = true, includeNames = true)
class SmtpProperties {

    String host
    String port
    String username
    String password
    String authentication
    String startTlsEnable
    String transportProtocol
    String mailDebug

    SmtpProperties() {
    }

    SmtpProperties(String host, String port,
                   String username, String password,
                   String authentication, String startTlsEnable,
                   String transportProtocol, String mailDebug) {
        this.host = host
        this.port = port
        this.username = username
        this.password = password
        this.authentication = authentication
        this.startTlsEnable = startTlsEnable
        this.transportProtocol = transportProtocol
        this.mailDebug = mailDebug
    }
}
