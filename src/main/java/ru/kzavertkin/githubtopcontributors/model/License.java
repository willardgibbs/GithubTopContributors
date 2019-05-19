package ru.kzavertkin.githubtopcontributors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class License implements Serializable {
    private String key;

    private String name;

    @JsonProperty("spdx_id")
    private String spdxId;

    private String url;

    @JsonProperty("node_id")
    private String nodeId;
}
