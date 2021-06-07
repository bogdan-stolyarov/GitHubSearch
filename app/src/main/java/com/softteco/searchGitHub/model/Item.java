package com.softteco.searchGitHub.model;

import com.google.gson.annotations.SerializedName;


public class Item {

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("description")
    private String description;

    @SerializedName("html_url")
    private String url;

    @SerializedName("forks_count")
    private int forksCount;

    @SerializedName("watchers_count")
    private int watchersCount;

    @SerializedName("stargazers_count")
    private  int stargazersCount;

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public int getForksCount() {
        return forksCount;
    }

    public int getWatchersCount() {
        return watchersCount;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

}
