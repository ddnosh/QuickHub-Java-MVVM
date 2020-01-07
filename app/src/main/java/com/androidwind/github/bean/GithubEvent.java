package com.androidwind.github.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class GithubEvent implements Parcelable {

    public enum EventType {

        CommitCommentEvent,
        CreateEvent,
        /**
         * Represents a deleted branch or tag.
         */
        DeleteEvent,
        ForkEvent,
        /**
         * Triggered when a Wiki page is created or updated.
         */
        GollumEvent,


        /**
         * Triggered when a GitHub App has been installed or uninstalled.
         */
        InstallationEvent,
        /**
         * Triggered when a repository is added or removed from an installation.
         */
        InstallationRepositoriesEvent,
        IssueCommentEvent,
        IssuesEvent,


        /**
         * Triggered when a user purchases, cancels, or changes their GitHub Marketplace plan.
         */
        MarketplacePurchaseEvent,
        /**
         * Triggered when a user is added or removed as a collaborator to a repository, or has their permissions changed.
         */
        MemberEvent,
        /**
         * Triggered when an organization blocks or unblocks a user.
         */
        OrgBlockEvent,
        /**
         * Triggered when a project card is created, updated, moved, converted to an issue, or deleted.
         */
        ProjectCardEvent,
        /**
         * Triggered when a project column is created, updated, moved, or deleted.
         */
        ProjectColumnEvent,


        /**
         * Triggered when a project is created, updated, closed, reopened, or deleted.
         */
        ProjectEvent,
        /**
         * made repository public
         */
        PublicEvent,
        PullRequestEvent,
        /**
         * Triggered when a pull request review is submitted into a non-pending state, the body is edited, or the review is dismissed.
         */
        PullRequestReviewEvent,
        PullRequestReviewCommentEvent,


        PushEvent,
        ReleaseEvent,
        WatchEvent,

        //Events of this type are not visible in timelines. These events are only used to trigger hooks.
        DeploymentEvent,
        DeploymentStatusEvent,
        MembershipEvent,
        MilestoneEvent,
        OrganizationEvent,
        PageBuildEvent,
        RepositoryEvent,
        StatusEvent,
        TeamEvent,
        TeamAddEvent,
        LabelEvent,

        //Events of this type are no longer delivered, but it's possible that they exist in timelines
        // of some users. You cannot createForRepo webhooks that listen to these events.
        DownloadEvent,
        FollowEvent,
        ForkApplyEvent,
        GistEvent,

    }

    private String id;
    private EventType type;
    private GithubActor actor;
    private GithubRepository repo;
    private GithubActor org;
    @SerializedName("created_at")
    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public GithubActor getActor() {
        return actor;
    }

    public void setActor(GithubActor actor) {
        this.actor = actor;
    }

    public GithubRepository getRepo() {
        return repo;
    }

    public void setRepo(GithubRepository repo) {
        this.repo = repo;
    }

    public GithubActor getOrg() {
        return org;
    }

    public void setOrg(GithubActor org) {
        this.org = org;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    protected GithubEvent(Parcel in) {
        id = in.readString();
        actor = in.readParcelable(GithubActor.class.getClassLoader());
        repo = in.readParcelable(GithubRepository.class.getClassLoader());
        org = in.readParcelable(GithubActor.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(actor, flags);
        dest.writeParcelable(repo, flags);
        dest.writeParcelable(org, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GithubEvent> CREATOR = new Creator<GithubEvent>() {
        @Override
        public GithubEvent createFromParcel(Parcel in) {
            return new GithubEvent(in);
        }

        @Override
        public GithubEvent[] newArray(int size) {
            return new GithubEvent[size];
        }
    };
}