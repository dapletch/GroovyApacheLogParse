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

}
