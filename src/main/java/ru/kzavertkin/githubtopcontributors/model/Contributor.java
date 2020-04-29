package ru.kzavertkin.githubtopcontributors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 */
@Data
@Accessors(chain = true)
public class Contributor implements Serializable {
    private int contributions;

    private int id;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private String login;

    private String type;

    private String url;

    @JsonProperty("node_id")
    private String nodeId;

    @JsonProperty("gravatar_id")
    private String gravatarId;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("followers_url")
    private String followersUrl;

    @JsonProperty("following_url")
    private String followingUrl;

    @JsonProperty("gists_url")
    private String gistsUrl;

    @JsonProperty("starred_url")
    private String starredUrl;

    @JsonProperty("subscriptions_url")
    private String subscriptionsUrl;

    @JsonProperty("organizations_url")
    private String organizationsUrl;

    @JsonProperty("repos_url")
    private String reposUrl;

    @JsonProperty("events_url")
    private String eventsUrl;

    @JsonProperty("received_events_url")
    private String receivedEventsUrl;

    @JsonProperty("site_admin")
    private boolean siteAdmin;
}
