package com.abnamro.assignment.api.crudtest;

import com.abnamro.assignment.api.dataprovider.BaseDataProvider;
import com.abnamro.assignment.utils.IssueAPIUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Soumya
 */
public class DeleteIssueTest extends CrudBase{

    String issueIID ;

    IssueAPIUtil issueAPIUtil = new IssueAPIUtil();
    @BeforeClass
    private void createIssueAndSaveIID() {
        try {
            issueIID = issueAPIUtil.createIssueAndReturnIid(requestMetaData.projectId, BaseDataProvider.createIssueProvider());
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Test(description = "Delete an Issue")
    public void deleteIssue() {
        String iid = issueAPIUtil.createIssueAndReturnIid(requestMetaData.projectId, BaseDataProvider.createIssueProvider());
        response = issueAPIUtil.deleteIssue(requestMetaData.projectId, iid);
        response.then().assertThat().statusCode(204);

        //confirm delete
        response = issueAPIUtil.getProjectIssue(requestMetaData.projectId, iid);
        response.then().assertThat().statusCode(404);
    }

    @Test(dataProvider = "invalidProjectId", dataProviderClass = BaseDataProvider.class, description = "Invalid Project Id should return a 404")
    public void invalidProjectId(Object data) {
        response = issueAPIUtil.deleteIssue(String.valueOf(data), issueIID);
        response.then().assertThat().statusCode(404);
    }

    @Test(dataProvider = "invalidIssueIid", dataProviderClass = BaseDataProvider.class, description = "Invalid Issue Id should return a 404")
    public void invalidIssueIid(Object data) {
        response = issueAPIUtil.deleteIssue(requestMetaData.projectId, String.valueOf(data));
        response.then().assertThat().statusCode(404);
    }
}
