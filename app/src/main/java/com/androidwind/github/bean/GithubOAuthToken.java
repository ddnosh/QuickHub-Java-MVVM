

package com.androidwind.github.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GithubOAuthToken {

    @SerializedName("access_token")
    private String accessToken;

    private String scope;

    public GithubOAuthToken() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
