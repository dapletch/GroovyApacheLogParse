package com.pletcherwebdesign.logparse.services.geolitecity

import com.pletcherwebdesign.logparse.beans.configuration.geolitecity.GeoLiteCityConfig
import com.pletcherwebdesign.logparse.beans.configuration.geolitecity.GeoLiteCityProperties
import com.pletcherwebdesign.logparse.utils.LogUtils
import com.pletcherwebdesign.logparse.utils.OSUtils
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

    private static GeoLiteCityProperties geoLiteCityProperties = context.getBean(GeoLiteCityProperties.class)

    private static def log = LoggerFactory.getLogger(ObtainGeoLiteCity.class)

    static def downloadGeoLiteCityFile() {
        try {
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

        return LogUtils.isFileValid(new File(geoLiteFile))
    }
}
