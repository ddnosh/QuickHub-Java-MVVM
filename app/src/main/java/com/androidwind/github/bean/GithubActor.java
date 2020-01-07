package com.androidwind.github.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GithubActor implements Parcelable {

    private String id;
    private String login;
    @SerializedName("display_login") private String displayLogin;
    @SerializedName("gravatar_id") private String gravatarId;
    private String url;
    @SerializedName("avatar_url") private String avatarUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDisplayLogin() {
        return displayLogin;
    }

    public void setDisplayLogin(String displayLogin) {
        this.displayLogin = displayLogin;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public void setGravatarId(String gravatarId) {
        this.gravatarId = gravatarId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    protected GithubActor(Parcel in) {
        id = in.readString();
        login = in.readString();
        displayLogin = in.readString();
        gravatarId = in.readString();
        url = in.readString();
        avatarUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(login);
        dest.writeString(displayLogin);
        dest.writeString(gravatarId);
        dest.writeString(url);
        dest.writeString(avatarUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GithubActor> CREATOR = new Creator<GithubActor>() {
        @Override
        public GithubActor createFromParcel(Parcel in) {
            return new GithubActor(in);
        }

        @Override
        public GithubActor[] newArray(int size) {
            return new GithubActor[size];
        }
    };
}
