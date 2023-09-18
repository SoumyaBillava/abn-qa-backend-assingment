package com.abnamro.assignment.api.domain;

import lombok.Getter;

/**
 * @author Soumya
 */
@Getter
public class RequestMetaData {

    public String projectId;

    public String baseUri;

    public String accessToken;

    public RequestMetaData(String baseUri, String projectId, String accessToken) {
        this.projectId = projectId;
        this.baseUri = baseUri;
        this.accessToken = accessToken;
    }

}
