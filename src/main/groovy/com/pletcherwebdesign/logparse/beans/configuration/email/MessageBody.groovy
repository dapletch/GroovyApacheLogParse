package com.pletcherwebdesign.logparse.beans.configuration.email

import groovy.transform.ToString

/**
 * Created by Seth on 9/24/2017.
 */
@ToString(includeNames = true, includeFields = true)
class MessageBody {

    String recipient
    String subject
    String message
    String attachment

    MessageBody() {
    }

    MessageBody(String recipient, String subject, String message) {
        this.recipient = recipient
        this.subject = subject
        this.message = message
    }

    MessageBody(String recipient, String subject, String message, String attachment) {
        this.recipient = recipient
        this.subject = subject
        this.message = message
        this.attachment = attachment
    }
}
