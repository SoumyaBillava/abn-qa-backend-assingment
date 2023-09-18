package com.abnamro.assignment.utils;

import com.relevantcodes.extentreports.ExtentReports;

/**
 * @author Soumya
 */
public class ExtentsSingleton {

    private static ExtentsSingleton INSTANCE = null;

    ExtentReports extentReports;
    private ExtentsSingleton() {}

    public static ExtentsSingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (ExtentsSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ExtentsSingleton();
                    INSTANCE.extentReports = new ExtentReports("./Report.html",true);
                }
            }
        }
        return INSTANCE;
    }
}
