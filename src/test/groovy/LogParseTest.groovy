import junit.framework.TestCase
import junit.framework.TestSuite

/**
 * Created by Seth on 9/20/2017.
 * Using JUNIT for integration/regression testing
 */
class LogParseTest extends TestCase {

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
}
