package com.pletcherwebdesign.logparse.beans.configuration.geolitecity

import groovy.transform.ToString

/**
 * Created by Seth on 9/23/2017.
 */

@ToString(includeFields = true, includeNames = true)
class GeoLiteCityProperties {

    String geoLiteFileUrl
    String geoLiteFileGz
    String geoLiteFile

    GeoLiteCityProperties(String geoLiteFileUrl, String geoLiteFileGz, String geoLiteFile) {
        this.geoLiteFileUrl = geoLiteFileUrl
        this.geoLiteFileGz = geoLiteFileGz
        this.geoLiteFile = geoLiteFile
    }
}
