package com.abnamro.assignment.api.crudtest;

import com.abnamro.assignment.api.dataprovider.BaseDataProvider;
import com.abnamro.assignment.api.models.request.CoreRequest;
import com.abnamro.assignment.utils.IssueAPIUtil;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * @author Soumya
 */
public class UpdateIssueTest extends CrudBase {

    IssueAPIUtil issueAPIUtil = new IssueAPIUtil();

    @Test(dataProvider = "editIssueTestData", dataProviderClass = BaseDataProvider.class, description = "Update Issue should be successful")
    public void editIssue(CoreRequest coreRequest) {
        String reqIID = issueAPIUtil.createIssueAndReturnIid(requestMetaData.projectId, BaseDataProvider.createIssueProvider());
        response = issueAPIUtil.editIssue(requestMetaData.projectId, reqIID, coreRequest.params);
        response.then().assertThat().statusCode(200);
    }

    @Test (description = "Update request returns 400 when no params are specified")
    public void editIssueNoParameter() {
        String reqIID = issueAPIUtil.createIssueAndReturnIid(requestMetaData.projectId, BaseDataProvider.createIssueProvider());
        response = issueAPIUtil.editIssue(requestMetaData.projectId, reqIID, new HashMap<>());
        response.then().assertThat().statusCode(400);
    }

}
