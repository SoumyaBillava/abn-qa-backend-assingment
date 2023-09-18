package com.abnamro.assignment.api.constants;

/**
 * @author Soumya
 */
public class IssueAPIConstants {

    public static String issuesUri = "/issues";

    public static String createIssueUri(String projectId){
        return "/projects/"+projectId+"/issues";
    }
    public static String createEditIssueUri(String projectId, String issueIid){
        return "/projects/"+projectId+"/issues/"+ issueIid;
    }
    public static String createDeleteIssueUri(String projectId, String issueIid){
        return "/projects/"+projectId+"/issues/"+ issueIid;
    }

    public static String createGetIssueUri(String projectId, String issueIid){
        return "/projects/"+projectId+"/issues/"+ issueIid;
    }

}
