package com.abnamro.assignment.api.crudtest;

import com.abnamro.assignment.api.domain.RequestMetaData;
import com.abnamro.assignment.utils.ConfigLoader;
import com.abnamro.assignment.utils.ExtentsReportUtil;
import com.abnamro.assignment.utils.IssueAPIUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author Soumya
 */
public class CrudBase {

    public static RequestMetaData requestMetaData;

    public Response response;

    private Properties prop;


    private IssueAPIUtil issueAPIUtil;
    @BeforeSuite
    public void initialize(){

        prop = new Properties();
        try {
            prop.load(new FileReader(ConfigLoader.file_config));
        } catch (IOException e) {
            ExtentsReportUtil.logError(e.getLocalizedMessage());
        }

        String baseUri = prop.getProperty("BASE_URI");
        String projectId = System.getenv("GITLAB_PROJECT_ID") != null ? System.getenv("GITLAB_PROJECT_ID") : prop.getProperty("GITLAB_PROJECT_ID");
        String accessToken = System.getenv("PERSONAL_ACCESS_TOKEN") != null ? System.getenv("PERSONAL_ACCESS_TOKEN") : prop.getProperty("PERSONAL_ACCESS_TOKEN");

        requestMetaData = new RequestMetaData(baseUri, projectId, accessToken);
        RestAssured.baseURI = baseUri;
        RestAssured.urlEncodingEnabled = true;
    }



    @BeforeTest
    public void beforeTest() {
        issueAPIUtil = new IssueAPIUtil();
        issueAPIUtil.deleteIssuesForProject(requestMetaData.projectId);
    }

    @BeforeMethod
    public void beforeMethod(Method method, Object[] testData) {
        ExtentsReportUtil.startTest(method.getName(), "Starting test for Issue API :" + requestMetaData.baseUri);
    }

    @AfterMethod
    public void afterMethod(ITestResult iTestResult) throws JsonProcessingException {
        if (iTestResult.getStatus() == ITestResult.FAILURE) {
            ExtentsReportUtil.getTest().log(LogStatus.FAIL, iTestResult.getMethod().getDescription());
            ExtentsReportUtil.logError("TEST FAILED - Error is: " + iTestResult.getThrowable());
        } else if (iTestResult.getStatus() == ITestResult.SKIP) {
            ExtentsReportUtil.getTest().log(LogStatus.SKIP, iTestResult.getMethod().getDescription());
            ExtentsReportUtil.logError("TEST SKIPPED - Error is" + iTestResult.getThrowable());
        } else {
            ExtentsReportUtil.getTest().log(LogStatus.PASS, iTestResult.getMethod().getDescription());
            ExtentsReportUtil.logPass("TEST PASSED \n");
        }

        ExtentsReportUtil.logInfo("Class name --> " + iTestResult.getTestClass().getRealClass().getName());  //log class name, method name in report
        ExtentsReportUtil.logInfo("Method name --> "+ iTestResult.getMethod().getMethodName());

        Object[] Object_Array= iTestResult.getParameters();
        String[] String_Array=new String[Object_Array.length];

        for (int i=0;i<String_Array.length;i++)
            ExtentsReportUtil.logInfo("Test parameters - " +new ObjectMapper().writeValueAsString(Object_Array[i]));

        if(response !=null) {
            ExtentsReportUtil.logInfo("Response code --> " + response.getStatusCode() + " " + response.getStatusLine());
            ExtentsReportUtil.logInfo("Response body --> " + response.getBody().asPrettyString());
        }
        ExtentsReportUtil.extent.endTest(ExtentsReportUtil.getTest());
        ExtentsReportUtil.extent.flush();
    }

}
