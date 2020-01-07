package com.androidwind.github.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GithubSearch {

    @SerializedName("total_count")
    private Integer totalCount;
    @SerializedName("incomplete_results")
    private Boolean incompleteResults;
    @SerializedName("items")
    private List<GithubRepository> repositories;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<GithubRepository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<GithubRepository> repositories) {
        this.repositories = repositories;
    }
}
