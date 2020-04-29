package ru.kzavertkin.githubtopcontributors.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GithubTopContributorsError {
    private String applicationName;
    private Integer errorCode;
    private String errorMessage;
}
