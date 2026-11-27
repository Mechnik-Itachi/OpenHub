package com.thirtydegreesray.openhub.mvp.contract;

import com.thirtydegreesray.openhub.mvp.contract.base.IBaseContract;
import com.thirtydegreesray.openhub.mvp.contract.base.IBaseListContract;
import com.thirtydegreesray.openhub.mvp.contract.base.IBasePagerContract;
import com.thirtydegreesray.openhub.mvp.model.WorkflowRun;

import java.util.ArrayList;

public interface IActionsContract {

    interface View extends IBaseContract.View, IBasePagerContract.View, IBaseListContract.View {
        void showWorkflowRuns(ArrayList<WorkflowRun> runs);
    }

    interface Presenter extends IBasePagerContract.Presenter<IActionsContract.View> {
        void loadWorkflowRuns(boolean isReload, int page);
    }

}
