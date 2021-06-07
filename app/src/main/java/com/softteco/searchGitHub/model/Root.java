package com.softteco.searchGitHub.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Root {

    @SerializedName("items")
    private List<Item> items;

    @SerializedName("total_count")
    private int totalCount;

    public List<Item> getItems() {
        return items;
    }

    public int getTotalCount() {
        return totalCount;
    }

}
