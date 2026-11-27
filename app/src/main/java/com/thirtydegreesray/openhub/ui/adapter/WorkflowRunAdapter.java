package com.thirtydegreesray.openhub.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.thirtydegreesray.openhub.R;
import com.thirtydegreesray.openhub.mvp.model.WorkflowRun;
import com.thirtydegreesray.openhub.ui.adapter.base.BaseAdapter;
import com.thirtydegreesray.openhub.ui.adapter.base.BaseViewHolder;
import com.thirtydegreesray.openhub.ui.fragment.base.BaseFragment;
import com.thirtydegreesray.openhub.util.StringUtils;

import javax.inject.Inject;

import butterknife.BindView;

public class WorkflowRunAdapter extends BaseAdapter<WorkflowRunAdapter.ViewHolder, WorkflowRun> {

    private static final int COLOR_SUCCESS = 0xFF2EA043;
    private static final int COLOR_FAILURE = 0xFFF85149;
    private static final int COLOR_PENDING = 0xFFD29922;
    private static final int COLOR_NEUTRAL = 0xFF8B949E;

    @Inject
    public WorkflowRunAdapter(Context context, BaseFragment fragment) {
        super(context, fragment);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.layout_item_workflow_run;
    }

    @Override
    protected ViewHolder getViewHolder(View itemView, int viewType) {
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        WorkflowRun run = data.get(position);

        String title = StringUtils.isBlank(run.getDisplayTitle()) ? run.getName() : run.getDisplayTitle();
        holder.title.setText(title);

        StringBuilder info = new StringBuilder();
        if (run.getName() != null) info.append(run.getName()).append(' ');
        info.append('#').append(run.getRunNumber());
        if (run.getEvent() != null) info.append(" \u00B7 ").append(run.getEvent());
        if (run.getCreatedAt() != null) {
            info.append(" \u00B7 ").append(StringUtils.getNewsTimeStr(context, run.getCreatedAt()));
        }
        holder.info.setText(info);

        holder.branch.setText(run.getHeadBranch() == null ? "" : run.getHeadBranch());

        int color;
        String statusText;
        String status = run.getStatus();
        String conclusion = run.getConclusion();
        if ("completed".equals(status) && conclusion != null) {
            switch (conclusion) {
                case "success":
                    color = COLOR_SUCCESS;
                    statusText = context.getString(R.string.workflow_status_success);
                    break;
                case "failure":
                case "timed_out":
                case "startup_failure":
                    color = COLOR_FAILURE;
                    statusText = context.getString(R.string.workflow_status_failure);
                    break;
                case "cancelled":
                    color = COLOR_NEUTRAL;
                    statusText = context.getString(R.string.workflow_status_cancelled);
                    break;
                case "skipped":
                    color = COLOR_NEUTRAL;
                    statusText = context.getString(R.string.workflow_status_skipped);
                    break;
                case "action_required":
                    color = COLOR_PENDING;
                    statusText = conclusion;
                    break;
                default:
                    color = COLOR_NEUTRAL;
                    statusText = conclusion;
                    break;
            }
        } else if ("in_progress".equals(status)) {
            color = COLOR_PENDING;
            statusText = context.getString(R.string.workflow_status_in_progress);
        } else if ("queued".equals(status) || "waiting".equals(status)
                || "pending".equals(status) || "requested".equals(status)) {
            color = COLOR_PENDING;
            statusText = context.getString(R.string.workflow_status_queued);
        } else {
            color = COLOR_NEUTRAL;
            statusText = status == null ? "" : status;
        }
        holder.statusText.setText(statusText);
        holder.statusText.setTextColor(color);
        holder.statusIcon.setColorFilter(color);
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.status_icon) AppCompatImageView statusIcon;
        @BindView(R.id.run_title) TextView title;
        @BindView(R.id.run_info) TextView info;
        @BindView(R.id.run_branch) TextView branch;
        @BindView(R.id.run_status) TextView statusText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
