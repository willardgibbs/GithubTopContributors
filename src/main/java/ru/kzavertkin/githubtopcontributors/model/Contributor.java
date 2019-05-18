package ru.kzavertkin.githubtopcontributors.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
public class Contributor implements Serializable {
    private int contributions;

    private int id;

    @SerializedName("avatar_url")
    private String avatarUrl;

    private String login;

    private String type;

    private String url;

    @SerializedName("node_id")
    private String nodeId;

    @SerializedName("gravatar_id")
    private String gravatarId;

    @SerializedName("html_url")
    private String htmlUrl;

    @SerializedName("followers_url")
    private String followersUrl;

    @SerializedName("following_url")
    private String followingUrl;

    @SerializedName("gists_url")
    private String gistsUrl;

    @SerializedName("starred_url")
    private String starredUrl;

    @SerializedName("subscription_url")
    private String subscriptionUrl;

    @SerializedName("organizations_url")
    private String organizations_url;

    @SerializedName("repos_url")
    private String reposUrl;

    @SerializedName("events_url")
    private String eventsUrl;

    @SerializedName("received_events_url")
    private String receivedEventsUrl;

    @SerializedName("site_admin")
    private String siteAdmin;
}
