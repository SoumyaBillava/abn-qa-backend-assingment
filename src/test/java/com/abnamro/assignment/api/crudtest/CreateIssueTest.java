package com.abnamro.assignment.api.crudtest;

import com.abnamro.assignment.api.constants.IssueAPIConstants;
import com.abnamro.assignment.api.dataprovider.BaseDataProvider;
import com.abnamro.assignment.api.models.request.CoreRequest;
import com.abnamro.assignment.api.models.request.CreateModel;
import com.abnamro.assignment.api.models.response.IssueModel;
import com.abnamro.assignment.utils.IssueAPIUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author Soumya
 */
public class CreateIssueTest extends CrudBase {

    IssueAPIUtil issueAPIUtil = new IssueAPIUtil();

    @Test(dataProvider = "createIssueTestData", dataProviderClass = BaseDataProvider.class, description = "Create an Issue")
    public void createIssue(CoreRequest coreRequest) {

        CreateModel createModel = coreRequest.createModel;
        response = issueAPIUtil.createIssue(requestMetaData.projectId, createModel);
        IssueModel actualResponse = response.as(IssueModel.class);

        Assert.assertNotNull(actualResponse.id);
        Assert.assertNotNull(actualResponse.projectId);
        Assert.assertEquals(actualResponse.title, createModel.title);
        Assert.assertEquals(actualResponse.description, createModel.description);
        Assert.assertEquals(actualResponse.state, "opened");
        Assert.assertEquals(actualResponse.type, "ISSUE");
        Assert.assertEquals(actualResponse.issueType, "issue");

        if(createModel.labels != null)
            Assert.assertEquals(actualResponse.labels, createModel.labels);
        else
            Assert.assertTrue(actualResponse.labels.isEmpty());

        if(createModel.confidential != null)
            Assert.assertEquals(actualResponse.confidential, createModel.confidential);
        else
            Assert.assertFalse(actualResponse.confidential);
    }

    @Test(dataProvider = "createIssueNoTitle", dataProviderClass = BaseDataProvider.class, description = "Create Issue fails on missing Title")
    public void createIssueNoTitle(CoreRequest coreRequest){
        response = issueAPIUtil.createIssue(requestMetaData.projectId, coreRequest.createModel);
        response.then().assertThat().statusCode(400);
    }

    @Test(dataProvider = "createIssueInvalidIssueType", dataProviderClass = BaseDataProvider.class, description = "Create Issue fails on incorrect issue type")
    public void createIssueInvalidIssueType(CoreRequest coreRequest){
        response = issueAPIUtil.createIssue(requestMetaData.projectId, coreRequest.createModel);
        response.then().assertThat().statusCode(400).body("error",equalTo("issue_type does not have a valid value"));
    }

    @Test (description = "Invalid access token should be rejected")
    public void createIssueInvalidToken() {
        response = issueAPIUtil.doPost(IssueAPIConstants.createIssueUri(requestMetaData.projectId),
                issueAPIUtil.invalidHeaderMap(), BaseDataProvider.createIssueProvider());
        response.then().assertThat().statusCode(401);
    }
}
