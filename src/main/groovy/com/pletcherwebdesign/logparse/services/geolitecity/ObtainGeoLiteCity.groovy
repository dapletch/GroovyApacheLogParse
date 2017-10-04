package com.pletcherwebdesign.logparse.services.geolitecity

import com.pletcherwebdesign.logparse.beans.configuration.email.MessageBody
import com.pletcherwebdesign.logparse.beans.configuration.geolitecity.GeoLiteCityConfig
import com.pletcherwebdesign.logparse.beans.configuration.geolitecity.GeoLiteCityProperties
import com.pletcherwebdesign.logparse.services.sendemail.SendEmail
import com.pletcherwebdesign.logparse.utils.LogUtils
import com.pletcherwebdesign.logparse.utils.OSUtils
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.stereotype.Service

import java.util.zip.GZIPInputStream

/**
 * Created by Seth on 9/22/2017.
 * This service is specifically meant to download the GeoLiteCity.date.gz file and decompress it
 * to be used to acquire more information in regards to the IP Address accessing http://www.pletcherwebdesign.com/
 */

@Service
class ObtainGeoLiteCity {

    private static def context = new AnnotationConfigApplicationContext(GeoLiteCityConfig.class)
    private static def geoLiteCityProperties = context.getBean(GeoLiteCityProperties.class)
    private static def message = context.getBean(MessageBody.class)
    private static def log = LoggerFactory.getLogger(ObtainGeoLiteCity.class)

    static def downloadGeoLiteCityFile() {
        try {
            // back up the preexisting GeoLiteCity.dat.gz file, just in case something goes wrong downloading the file
            backUpGeoLiteCityFile()
            // downloading the file GeoLiteCity.dat.gz file
            log.info("GeoLiteCityProperties: " + geoLiteCityProperties.toString())
            def geoLiteFile = "${OSUtils.filePathAboveCurrentOne}${geoLiteCityProperties.geoLiteFileGz}"
            log.info("GeoLiteFileGz: " + geoLiteFile)
            def file = new File("${geoLiteFile}").newOutputStream()
            file << new URL("${geoLiteCityProperties.geoLiteFileUrl}${geoLiteCityProperties.geoLiteFileGz}").openStream()
            file.close()
            // extracting the GeoLiteCit.dat.gz file, returning true if no exception thrown
            return extractGeoLiteCityFile()
        } catch (Exception e) {
            log.error("An exception was thrown while obtaining ${geoLiteCityProperties.geoLiteFileGz}", e)
            SendEmail.sendEmailNoAttachmentIncluded(
                    new MessageBody(message.recipient,
                            "An exception occurred while obtaining ${geoLiteCityProperties.geoLiteFileGz} file",
                            "There was an exception thrown while obtaining the GeoLiteCity.dat file: \n ${e.toString()}"
                    )
            )
            return false
        }
    }

    static def extractGeoLiteCityFile() {

        def fis = new FileInputStream("${OSUtils.filePathAboveCurrentOne}${geoLiteCityProperties.geoLiteFileGz}")
        def gis = new GZIPInputStream(fis)

        def geoLiteFile = "${OSUtils.filePathAboveCurrentOne}${geoLiteCityProperties.geoLiteFile}"
        def fos = new FileOutputStream("${geoLiteFile}")

        Integer oneByte
        while ((oneByte = gis.read()) != -1) {
            fos.write(oneByte)
        }

        log.info("GeoLiteCity File Successfully downloaded: " + geoLiteFile)
        // closing resources
        fis.close()
        gis.close()
        fos.close()

        // setting the GeoLiteCity.dat file to read only for security purposes
        def geoLiteFileWithPermissions = new File(geoLiteFile).setReadable(true, true)
        if (geoLiteFileWithPermissions) {
            return LogUtils.isFileValid(new File(geoLiteFile))
        }
        return false
    }

    // creating this method just in case something goes wrong with downloading the file
    static def backUpGeoLiteCityFile() {
        def dateStr = LogUtils.formatDateTimeForLog(new DateTime())
        def geoLiteFile = new File("${OSUtils.filePathAboveCurrentOne}${geoLiteCityProperties.geoLiteFile}")
        def geoLiteFileBackup = new File("${OSUtils.filePathAboveCurrentOne}${geoLiteCityProperties.geoLiteFile}.${dateStr}")
        if (LogUtils.isFileValid(geoLiteFile)) {
            // renaming file to GeoLiteCity.dat.dateStr
            log.info("Renaming the ${geoLiteFile} to ${geoLiteFileBackup}")
            geoLiteFile.renameTo(geoLiteFileBackup)
        }
    }
}
