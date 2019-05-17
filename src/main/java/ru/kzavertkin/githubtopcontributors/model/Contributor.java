package ru.kzavertkin.githubtopcontributors.model;

import lombok.Data;

/**
 *
 */
@Data
public class Contributor {
    private int contributions;

    private int id;

    private String avatarUrl;

    private String login;

    private String type;

    private String url;
}
