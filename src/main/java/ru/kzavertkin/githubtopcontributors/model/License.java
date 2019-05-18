package ru.kzavertkin.githubtopcontributors.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class License implements Serializable {
    private String key;

    private String name;

    @SerializedName("spdx_id")
    private String spdxId;

    private String url;

    @SerializedName("node_id")
    private String nodeId;
}
