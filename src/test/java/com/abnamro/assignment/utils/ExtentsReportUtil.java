package com.abnamro.assignment.utils;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Soumya
 */
public class ExtentsReportUtil {

    public static ExtentReports extent = ExtentsSingleton.getInstance().extentReports;

    static Map<Integer, ExtentTest> tests = new HashMap<>();

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.startTest(testName, desc);
        tests.put((int)(Thread.currentThread().getId()), test);
        return test;
    }

    //To End the Testcase in Extent Report
    public static synchronized void endTest() {
        extent.endTest(tests.get((int) (Thread.currentThread().getId())));
    }

    //To get the Testcase from Extent
    public static synchronized ExtentTest getTest() {
        return tests.get((int)(Thread.currentThread().getId()));
    }

    /**
     * Prints Log Information to report
     * @param info
     */
    public static void logInfo(String info) {
        getTest().log(LogStatus.INFO, info);
    }

    /**
     * Prints Test status as Pass to report
     * @param info
     */
    public static void logPass(String info) {
        getTest().log(LogStatus.PASS, info);
    }

    /**
     * Prints Test status as Error to report
     * @param error
     */
    public static void logError(String error) {
        getTest().log(LogStatus.ERROR, error);
    }

    /**
     * Prints Test status as warning to report
     * @param error
     */
    public static void logWarn(String error) {
        getTest().log(LogStatus.WARNING, error);
    }
}
