package com.thirtydegreesray.openhub.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * GitHub Actions workflow run.
 * GET /repos/{owner}/{repo}/actions/runs
 */
public class WorkflowRun {

    private long id;
    private String name;
    @SerializedName("display_title") private String displayTitle;
    @SerializedName("run_number") private int runNumber;
    private String event;
    //queued, in_progress, completed, waiting, requested, pending
    private String status;
    //success, failure, cancelled, skipped, timed_out, neutral, action_required...
    private String conclusion;
    @SerializedName("head_branch") private String headBranch;
    @SerializedName("head_sha") private String headSha;
    @SerializedName("html_url") private String htmlUrl;
    @SerializedName("created_at") private Date createdAt;
    @SerializedName("updated_at") private Date updatedAt;
    private User actor;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public String getEvent() {
        return event;
    }

    public String getStatus() {
        return status;
    }

    public String getConclusion() {
        return conclusion;
    }

    public String getHeadBranch() {
        return headBranch;
    }

    public String getHeadSha() {
        return headSha;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public User getActor() {
        return actor;
    }
}
