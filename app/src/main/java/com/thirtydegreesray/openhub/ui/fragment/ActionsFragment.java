package com.thirtydegreesray.openhub.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.thirtydegreesray.openhub.R;
import com.thirtydegreesray.openhub.inject.component.AppComponent;
import com.thirtydegreesray.openhub.inject.component.DaggerFragmentComponent;
import com.thirtydegreesray.openhub.inject.module.FragmentModule;
import com.thirtydegreesray.openhub.mvp.contract.IActionsContract;
import com.thirtydegreesray.openhub.mvp.model.WorkflowRun;
import com.thirtydegreesray.openhub.mvp.presenter.ActionsPresenter;
import com.thirtydegreesray.openhub.ui.adapter.WorkflowRunAdapter;
import com.thirtydegreesray.openhub.ui.fragment.base.ListFragment;
import com.thirtydegreesray.openhub.util.AppOpener;
import com.thirtydegreesray.openhub.util.BundleHelper;

import java.util.ArrayList;

/**
 * GitHub Actions tab of the repository: list of workflow runs.
 */
public class ActionsFragment extends ListFragment<ActionsPresenter, WorkflowRunAdapter>
        implements IActionsContract.View {

    public static ActionsFragment create(@NonNull String user, @NonNull String repo) {
        ActionsFragment fragment = new ActionsFragment();
        fragment.setArguments(BundleHelper.builder()
                .put("user", user).put("repo", repo).build());
        return fragment;
    }

    @Override
    protected void initFragment(Bundle savedInstanceState) {
        super.initFragment(savedInstanceState);
        setLoadMoreEnable(true);
    }

    @Override
    public void showWorkflowRuns(ArrayList<WorkflowRun> runs) {
        adapter.setData(runs);
        postNotifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onReLoadData() {
        mPresenter.loadWorkflowRuns(true, 1);
    }

    @Override
    protected String getEmptyTip() {
        return getString(R.string.no_workflow_runs);
    }

    @Override
    protected void onLoadMore(int page) {
        super.onLoadMore(page);
        mPresenter.loadWorkflowRuns(false, page);
    }

    @Override
    public void onFragmentShowed() {
        super.onFragmentShowed();
        if (mPresenter != null) mPresenter.prepareLoadData();
    }

    @Override
    public void onItemClick(int position, @NonNull View view) {
        super.onItemClick(position, view);
        WorkflowRun run = adapter.getData().get(position);
        if (run.getHtmlUrl() != null) {
            AppOpener.openInCustomTabsOrBrowser(getActivity(), run.getHtmlUrl());
        }
    }
}
