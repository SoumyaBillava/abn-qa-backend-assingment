package com.abnamro.assignment.api.crudtest;

import com.abnamro.assignment.api.constants.IssueAPIConstants;
import com.abnamro.assignment.api.dataprovider.BaseDataProvider;
import com.abnamro.assignment.api.models.request.CreateModel;
import com.abnamro.assignment.utils.IssueAPIUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Soumya
 */
public class ReadIssuesTest extends CrudBase {

    IssueAPIUtil issueAPIUtil = new IssueAPIUtil();

    @Test(description = "List all the issues")
    public void listAllIssues() {
        response = issueAPIUtil.getAllIssues(IssueAPIConstants.createIssueUri(requestMetaData.projectId));
        response
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test (description = "Get Issues by label")
    public void getIssuesByLabel() {
        List<String> labels = new ArrayList<>();
        labels.add("p-111");
        CreateModel createModel = BaseDataProvider.createIssueProvider();
        createModel.setLabels((labels));

        issueAPIUtil.createIssueAndReturnIid(requestMetaData.projectId, createModel);

        response = issueAPIUtil.getAllIssues(IssueAPIConstants.createIssueUri(requestMetaData.projectId)+ "?labels=p-111");
        response.then()
                .assertThat()
                .statusCode(200);

        List<List<String>> labelList = response.jsonPath().getList("labels");
        for ( List<String> label: labelList) {
            Assert.assertTrue(label.contains("p-111"));
        }
    }
}
