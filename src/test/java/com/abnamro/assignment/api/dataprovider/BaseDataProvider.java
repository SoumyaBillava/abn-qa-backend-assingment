package com.abnamro.assignment.api.dataprovider;

import com.abnamro.assignment.api.models.request.CoreRequest;
import com.abnamro.assignment.api.models.request.CreateModel;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author Soumya
 */
public class BaseDataProvider {

    public static CreateModel createIssueProvider() {

        return CreateModel
                .builder()
                .title("Title of the issue")
                .confidential(false)
                .build();
    }

    @DataProvider(name = "createIssueTestData")
    public static Iterator<Object> dataProvider() {

        List<Object> testDataList = new ArrayList<>();

        CoreRequest firstModel =  new CoreRequest();
        firstModel.testName = "Create Non Confidential Issue";
        firstModel.createModel =  CreateModel
                .builder()
                .title("Title of the issue")
                .description("Description of the issue")
                .confidential(false)
                .build();


        testDataList.add(firstModel);

        CoreRequest secondModel =  new CoreRequest();
        secondModel.testName = "Create_confidential_issue_with_service_label";
        secondModel.createModel = CreateModel
                .builder()
                .title("Title of the issue")
                .labels(List.of("P1"))
                .confidential(true)
                .build();

        testDataList.add(secondModel);

        return testDataList.iterator();
    }

    @DataProvider(name = "createIssueNoTitle")
    public Object[] createIssueNoTitle()  {
        CoreRequest model =  new CoreRequest();
        model.testName = "Create Issue with no title";
        model.createModel =  CreateModel
                .builder()
                .description("Description of the issue")
                .confidential(false)
                .build();
        return new Object[]{model};
    }


    @DataProvider(name = "createIssueInvalidIssueType")
    public Object[] createIssueInvalidIssueType()  {
        CoreRequest model =  new CoreRequest();
        model.testName = "Create Issue with invalid issue type";
        model.createModel =  CreateModel
                .builder()
                .title("Title of this issue")
                .description("Description of the issue")
                .issueType("NotAllowedIssueType")
                .confidential(false)
                .build();
        return new Object[]{model};
    }

    @DataProvider(name = "invalidProjectId")
    public Object[] invalidProjectId() {
        Object[] data = { -003 };
        return data;
    }

    @DataProvider(name = "invalidIssueIid")
    public Object[] invalidIssueId() {
        Object[] data = { -112 };
        return data;
    }

    @DataProvider(name = "editIssueTestData")
    public static Iterator<Object> editDataProvider() {

        List<Object> objectList = new ArrayList<>();

        CoreRequest model1 = new CoreRequest();
        model1.testName = "Title Update test";
        model1.params =  new HashMap<>();
        model1.params.put("title", "Updated title of Issue");
        objectList.add(model1);

        CoreRequest model2 = new CoreRequest();
        model2.testName = "State update test";
        model2.params = new HashMap<>();
        model2.params.put("state_event", "close");
        objectList.add(model2);

        return objectList.iterator();

    }
}
