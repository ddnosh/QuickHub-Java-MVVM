package com.androidwind.github.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GithubRepository implements Parcelable {

    private String id;

    private String name;

    private String url;

    @SerializedName("created_at") String createdAt;
    private String description;
    private @SerializedName("full_name") String fullName;
    private String homepage;
    private @SerializedName("html_url") String htmlUrl;
    private String language;
    private GithubUser owner;
    private Double score;
    private @SerializedName("stargazers_count") Integer stargazersCount;
    private @SerializedName("updated_at") String updatedAt;
    private Integer watchers;

    protected GithubRepository(Parcel in) {
        id = in.readString();
        name = in.readString();
        url = in.readString();
        createdAt = in.readString();
        description = in.readString();
        fullName = in.readString();
        homepage = in.readString();
        htmlUrl = in.readString();
        language = in.readString();
        if (in.readByte() == 0) {
            score = null;
        } else {
            score = in.readDouble();
        }
        if (in.readByte() == 0) {
            stargazersCount = null;
        } else {
            stargazersCount = in.readInt();
        }
        updatedAt = in.readString();
        if (in.readByte() == 0) {
            watchers = null;
        } else {
            watchers = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(createdAt);
        dest.writeString(description);
        dest.writeString(fullName);
        dest.writeString(homepage);
        dest.writeString(htmlUrl);
        dest.writeString(language);
        if (score == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(score);
        }
        if (stargazersCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(stargazersCount);
        }
        dest.writeString(updatedAt);
        if (watchers == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(watchers);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GithubRepository> CREATOR = new Creator<GithubRepository>() {
        @Override
        public GithubRepository createFromParcel(Parcel in) {
            return new GithubRepository(in);
        }

        @Override
        public GithubRepository[] newArray(int size) {
            return new GithubRepository[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public GithubUser getOwner() {
        return owner;
    }

    public void setOwner(GithubUser owner) {
        this.owner = owner;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(Integer stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getWatchers() {
        return watchers;
    }

    public void setWatchers(Integer watchers) {
        this.watchers = watchers;
    }
}
