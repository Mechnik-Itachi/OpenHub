package com.thirtydegreesray.openhub.mvp.presenter;

import com.thirtydegreesray.dataautoaccess.annotation.AutoAccess;
import com.thirtydegreesray.openhub.dao.DaoSession;
import com.thirtydegreesray.openhub.http.core.HttpObserver;
import com.thirtydegreesray.openhub.http.core.HttpResponse;
import com.thirtydegreesray.openhub.http.error.HttpPageNoFoundError;
import com.thirtydegreesray.openhub.mvp.contract.IActionsContract;
import com.thirtydegreesray.openhub.mvp.model.WorkflowRun;
import com.thirtydegreesray.openhub.mvp.model.WorkflowRunsResponse;
import com.thirtydegreesray.openhub.mvp.presenter.base.BasePagerPresenter;
import com.thirtydegreesray.openhub.util.StringUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

public class ActionsPresenter extends BasePagerPresenter<IActionsContract.View>
        implements IActionsContract.Presenter {

    private static final int PER_PAGE = 30;

    @AutoAccess String user;
    @AutoAccess String repo;

    private ArrayList<WorkflowRun> runs;

    @Inject
    public ActionsPresenter(DaoSession daoSession) {
        super(daoSession);
    }

    @Override
    protected void loadData() {
        loadWorkflowRuns(false, 1);
    }

    @Override
    public void loadWorkflowRuns(final boolean isReload, final int page) {
        mView.showLoading();
        HttpObserver<WorkflowRunsResponse> httpObserver = new HttpObserver<WorkflowRunsResponse>() {
            @Override
            public void onError(Throwable error) {
                mView.hideLoading();
                if (!StringUtils.isBlankList(runs)) {
                    mView.showErrorToast(getErrorTip(error));
                } else if (error instanceof HttpPageNoFoundError) {
                    mView.showWorkflowRuns(new ArrayList<WorkflowRun>());
                } else {
                    mView.showLoadError(getErrorTip(error));
                }
            }

            @Override
            public void onSuccess(HttpResponse<WorkflowRunsResponse> response) {
                mView.hideLoading();
                ArrayList<WorkflowRun> list = response.body().getWorkflowRuns();
                if (list == null) {
                    list = new ArrayList<>();
                }
                if (runs == null || isReload || page == 1) {
                    runs = list;
                } else {
                    runs.addAll(list);
                }
                if (list.size() == 0 && runs.size() != 0) {
                    mView.setCanLoadMore(false);
                } else {
                    mView.showWorkflowRuns(runs);
                }
            }
        };
        //run statuses change quickly, so always read from network (readCacheFirst = false)
        generalRxHttpExecute(new IObservableCreator<WorkflowRunsResponse>() {
            @Override
            public Observable<Response<WorkflowRunsResponse>> createObservable(boolean forceNetWork) {
                return getActionsService().getWorkflowRuns(forceNetWork, user, repo, page, PER_PAGE);
            }
        }, httpObserver, false);
    }

    public String getUser() {
        return user;
    }

    public String getRepo() {
        return repo;
    }
}
