package com.thirtydegreesray.openhub.http;

import android.support.annotation.NonNull;

import com.thirtydegreesray.openhub.mvp.model.WorkflowRunsResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * GitHub Actions API.
 */
public interface ActionsService {

    @NonNull @GET("repos/{owner}/{repo}/actions/runs")
    Observable<Response<WorkflowRunsResponse>> getWorkflowRuns(
            @Header("forceNetWork") boolean forceNetWork,
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Query("page") int page,
            @Query("per_page") int perPage
    );

}
