package com.pletcherwebdesign.logparse.beans.model

import groovy.transform.ToString
import org.joda.time.DateTime

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

/**
 * Created by Seth on 9/20/2017.
 */
@Entity
@ToString(includeNames = true, includeFields = true, includePackage = true)
class LogRecord {

    @Id
    Long id

    @Column(name = "ip_address")
    String ipAddress

    @Column(name = "remote_user")
    String remoteUser

    @Column(name = "time_accessed")
    DateTime timeAccessed

    @Column(name = "request")
    String request

    @Column(name = "stat_cd")
    Integer statCode

    @Column(name = "bytes_sent")
    Integer bytesSent

    @Column(name = "time_entered")
    DateTime timeEntered

    LogRecord() {
    }

    LogRecord(Long id, String ipAddress, String remoteUser, DateTime timeAccessed, String request, Integer statCode, Integer bytesSent, DateTime timeEntered) {
        this.id = id
        this.ipAddress = ipAddress
        this.remoteUser = remoteUser
        this.timeAccessed = timeAccessed
        this.request = request
        this.statCode = statCode
        this.bytesSent = bytesSent
        this.timeEntered = timeEntered
    }
}
