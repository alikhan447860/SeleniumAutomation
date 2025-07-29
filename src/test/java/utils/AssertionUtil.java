package utils;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssertionUtil {

    private static final Logger logger = LoggerFactory.getLogger(AssertionUtil.class);

    public static void assertEquals(String message, String expected, String actual) {
        logger.info("Asserting: {} | Expected: [{}], Actual: [{}]", message, expected, actual);
        try {
            Assert.assertEquals(message, expected, actual);
            logger.info("Assertion PASSED: {}", message);
        } catch (AssertionError e) {
            logger.error("Assertion FAILED: {} | Expected: [{}], Actual: [{}]", message, expected, actual);
            throw e; // rethrow so test fails
        }
    }

    public static void assertTrue(String message, boolean condition) {
        logger.info("Asserting: {}", message);
        try {
            Assert.assertTrue(message, condition);
            logger.info("Assertion PASSED: {}", message);
        } catch (AssertionError e) {
            logger.error("Assertion FAILED: {}", message);
            throw e;
        }
    }
    public static void fail(String message) {
        Assert.fail("Assertion failed");
    }
}
