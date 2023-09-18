package com.abnamro.assignment.api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Soumya
 */
public class AuthorModel {
    public Integer id;

    public String username;

    public String name;

    public String state;
    @JsonProperty("avatar_url")
    public String avatarUrl;
    @JsonProperty("web_url")
    public String webUrl;
}
