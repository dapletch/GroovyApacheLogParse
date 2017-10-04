import com.pletcherwebdesign.logparse.beans.configuration.email.MessageBody
import com.pletcherwebdesign.logparse.services.sendemail.SendEmail
import com.pletcherwebdesign.logparse.services.accesslogs.ConcatenateAccessLogs
import com.pletcherwebdesign.logparse.services.geolitecity.ObtainGeoLiteCity
import com.pletcherwebdesign.logparse.utils.LogUtils
import com.pletcherwebdesign.logparse.utils.OSUtils
import junit.framework.TestCase
import junit.framework.TestSuite
import org.junit.Test
import org.slf4j.LoggerFactory

/**
 * Created by Seth on 9/20/2017.
 * Using JUNIT for integration/regression testing
 */

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

    void testConcatenateFiles() {
        def concatenateFiles = new ConcatenateAccessLogs()
        assertTrue(concatenateFiles.concatenateAccessLogs())
    }

    void testSendEmailNoAttachment() {
        def message = new MessageBody(
                "seth.pletcher@gmail.com",
                "Testing123",
                "Testing the email configuration."
        )
        log.info("Message: " + message.toString())
        SendEmail.sendEmailNoAttachmentIncluded(message)
    }

    void testSendEmailAttachmentIncluded() {
        // might have to run IDE/Intellij as administrator if you are sending an attachment
        def message = new MessageBody(
                "seth.pletcher@gmail.com",
                "Testing123",
                "Testing the email configuration.",
                new File("${OSUtils.filePathAboveCurrentOne}testEmail.txt").toString()
        )
        log.info("Message: " + message.toString())
        SendEmail.sendEmailAttachmentIncluded(message)
    }

    void testSendEmailIfExceptionThrown() {
        def exceptionThrown
        def message = new MessageBody(
                "seth.pletcher@gmail.com",
                "Testing123",
                "Testing the email configuration."
        )
        try {
            throw new Exception("An exception was thrown")
        } catch (Exception e) {
            exceptionThrown = true
            log.info("An exception was thrown", e)
            SendEmail.sendEmailNoAttachmentIncluded(message)
        }
        assertTrue(exceptionThrown)
    }


}
