package com.thirtydegreesray.openhub.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Wrapper of the workflow runs list response.
 */
public class WorkflowRunsResponse {

    @SerializedName("total_count") private int totalCount;
    @SerializedName("workflow_runs") private ArrayList<WorkflowRun> workflowRuns;

    public int getTotalCount() {
        return totalCount;
    }

    public ArrayList<WorkflowRun> getWorkflowRuns() {
        return workflowRuns;
    }
}
