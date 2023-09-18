package com.abnamro.assignment.utils;

import com.abnamro.assignment.api.crudtest.CrudBase;
import com.abnamro.assignment.api.models.request.CreateModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.abnamro.assignment.api.constants.IssueAPIConstants.*;

/**
 * @author Soumya
 */
public class IssueAPIUtil {


    public String createIssueAndReturnIid(String projectId, CreateModel createRequestModel) {
        Response response = doPost(createIssueUri(projectId), headerMap(), createRequestModel);
        response.then().statusCode(201);

        return response.jsonPath().getString("iid");
    }

    public Response createIssue(String projectId, CreateModel createRequestModel) {
        return doPost(createIssueUri(projectId), headerMap(), createRequestModel);
    }

    public Response getAllIssues(String requestPath) {
        return doGet(requestPath, headerMap());
    }


    public Response getProjectIssue(String projectId,String issueIID) {
        return doGet(createGetIssueUri(projectId,issueIID), headerMap());
    }
    public Response deleteIssue(String projectId, String issueIid) {
        return doDelete(createDeleteIssueUri(projectId, issueIid), headerMap());
    }

    public Response editIssue(String projectId, String issueIid, Map<String, String> queryParams) {
        return doPut(createEditIssueUri(projectId, issueIid), headerMap(), queryParams);
    }
    public void deleteIssuesForProject(String projectId) {
        Response response = getAllIssues(issuesUri);
        response.then().statusCode(200);
        List<Integer> iids;
        iids = response.jsonPath().get("iid");
        for ( Integer iid: iids) {
            deleteIssue(projectId, String.valueOf(iid));
        }
    }
    public synchronized Response doGet(String path, Map<String, String> headers){

        return RestAssured.given()
                .headers(headers)
                .when().log().all()
                .get(path)
                .then().log().body()
                .extract()
                .response();
    }

    public synchronized Response doPut(String path, Map<String, String> headers, Map<String, String> queryParams){

        return RestAssured.given()
                .headers(headers)
                .queryParams(queryParams)
                .when().log().all()
                .put(path)
                .then().log().body()
                .extract()
                .response();
    }

    public synchronized Response doPost(String path , Map<String, String> headers, Object body){

        return RestAssured.given()
                .headers(headers)
                .body(body)
                .when().log().all()
                .post(path)
                .then().log().body()
                .extract()
                .response();
    }

    public synchronized Response doDelete(String path, Map<String, String> headers){

        return RestAssured.given()
                .headers(headers)
                .when().log().all()
                .delete(path)
                .then().log().body()
                .extract()
                .response();
    }

    public Map<String,String> headerMap() {
        Map<String, String> header = new HashMap<>();
        header.put("PRIVATE-TOKEN", CrudBase.requestMetaData.accessToken);
        header.put("Content-Type", "application/json");
        return header;
    }

    public Map<String,String> invalidHeaderMap() {
        Map<String, String> header = new HashMap<>();
        header.put("PRIVATE-TOKEN", "invalid-token");
        header.put("Content-Type", "application/json");
        return header;
    }

}
