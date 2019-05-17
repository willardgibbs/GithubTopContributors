package ru.kzavertkin.githubtopcontributors.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Data
public class Repository implements Serializable {

    private boolean fork;

    private boolean hasDownloads;

    private boolean hasIssues;

    private boolean hasWiki;

    @SerializedName("private")
    private boolean isPrivate;

    private Date createdAt;

    private Date pushedAt;

    private Date updatedAt;

    private int forks;

    private long id;

    private int openIssues;

    private int size;

    private int stargazersCount;

    private int watchers;
    /**
     * This is what GitHub shows as "watchers".
     */
    private int subscribersCount = -1;

    private Repository parent;

    private Repository source;

    private String cloneUrl;

    private String description;

    private String homepage;

    private String gitUrl;

    private String htmlUrl;

    private String language;

    private String defaultBranch;

    private String mirrorUrl;

    private String name;

    private String sshUrl;

    private String svnUrl;

    private String url;

    private User owner;
}
