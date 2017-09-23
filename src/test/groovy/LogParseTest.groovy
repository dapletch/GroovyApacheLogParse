import com.pletcherwebdesign.logparse.beans.configuration.geolitecity.GeoLiteCityConfig
import com.pletcherwebdesign.logparse.beans.configuration.geolitecity.GeoLiteCityProperties
import com.pletcherwebdesign.logparse.services.geolitecity.ObtainGeoLiteCity
import junit.framework.TestCase
import junit.framework.TestSuite
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan

/**
 * Created by Seth on 9/20/2017.
 * Using JUNIT for integration/regression testing
 */

@ComponentScan
class LogParseTest extends TestCase {

    def log = LoggerFactory.getLogger(LogParseTest.class)
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    LogParseTest(String testCase) {
        super(testCase)
    }

    /**
     * @return the suite of tests being tested
     */
    static LogParseTest suite() {
        return new TestSuite(LogParseTest.class) as LogParseTest
    }

    /**
     * Rigourous Test :-)
     */
    void testApp() {
        assertTrue(true)
    }

    void testGeoLiteCityDownload() {
        def obtainGeoLiteCity = new ObtainGeoLiteCity()
        // testing if everything succeeded, and there was no exception thrown
        assertTrue(obtainGeoLiteCity.downloadGeoLiteCityFile())
    }
}
