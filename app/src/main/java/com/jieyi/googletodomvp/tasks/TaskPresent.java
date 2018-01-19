package com.jieyi.googletodomvp.tasks;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jieyi.googletodomvp.addedittask.AddeditFragment;
import com.jieyi.googletodomvp.addedittask.AddeditTaskActivity;
import com.jieyi.googletodomvp.model.beans.Task;
import com.jieyi.googletodomvp.model.source.TasksDataSource;
import com.jieyi.googletodomvp.model.source.TasksRepository;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * ***********************************************
 * **                  _oo0oo_                  **
 * **                 o8888888o                 **
 * **                 88" . "88                 **
 * **                 (| -_- |)                 **
 * **                 0\  =  /0                 **
 * **               ___/'---'\___               **
 * **            .' \\\|     |// '.             **
 * **           / \\\|||  :  |||// \\           **
 * **          / _ ||||| -:- |||||- \\          **
 * **          | |  \\\\  -  /// |   |          **
 * **          | \_|  ''\---/''  |_/ |          **
 * **          \  .-\__  '-'  __/-.  /          **
 * **        ___'. .'  /--.--\  '. .'___        **
 * **     ."" '<  '.___\_<|>_/___.' >'  "".     **
 * **    | | : '-  \'.;'\ _ /';.'/ - ' : | |    **
 * **    \  \ '_.   \_ __\ /__ _/   .-' /  /    **
 * **====='-.____'.___ \_____/___.-'____.-'=====**
 * **                  '=---='                  **
 * ***********************************************
 * **              佛祖保佑  永无Bug              **
 * ***********************************************
 */

public class TaskPresent implements TaskContract.Present {

    private TasksRepository mTasksRepository;//model层
    private TaskContract.View mTaskView;

    private TaskFilterType mTaskCurrentType = TaskFilterType.ALL_TASKS;
    private boolean isFirstLoad = true;

    public TaskPresent(TasksRepository mTasksRepository, TaskContract.View mTaskView) {
        this.mTasksRepository = checkNotNull(mTasksRepository, "tasksRepository 为null");
        this.mTaskView = checkNotNull(mTaskView,"mTaskView 为空");
        mTaskView.setPresent(this);
    }

    @Override
    public void start() {
       loadTasks(false);
    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        loadTasks(forceUpdate|| isFirstLoad,true);
        isFirstLoad = true;


    }

    private void loadTasks(boolean forceUpdate, final boolean ishowLoadingUI){

        if(ishowLoadingUI){
            mTaskView.setShowLoadingProgress(true);
        }
        if(forceUpdate){
            mTasksRepository.refreshTasks();
        }

        //由moudel层获取处理数据
        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {

                ArrayList<Task> datas = new ArrayList<>();

                for (Task task:tasks) {

                    switch (mTaskCurrentType){

                        case ALL_TASKS:
                           datas.add(task);
                            break;
                        case COMPLETED_TASKS:
                            if (task.isCompleted())datas.add(task);
                            break;
                        case ACTIVE_TASKS:
                        if(task.isActive())datas.add(task);
                            break;
                    }
                }
                Log.e("zyh","taskPresenter----> ");
                if(!mTaskView.isActive()){
                    return;
                }
                if(ishowLoadingUI)mTaskView.setShowLoadingProgress(false);
                Log.e("zyh","taskPresenter--size--> "+datas.size());
                processTasks(datas);
            }

            @Override
            public void onDataNotAvailable() {
               mTaskView.showLoadingTaskError();
            }
        });


    }

    private void processTasks(ArrayList<Task> datas) {

        if(datas.isEmpty()){
            processEmptyTask();
        }else {
            mTaskView.showTasks(datas);
            showFilterLable();
        }


    }

    private void showFilterLable() {
        switch (mTaskCurrentType){
            case ALL_TASKS:
                mTaskView.showNoTasks();
                break;
            case COMPLETED_TASKS:
                mTaskView.showNoCompletedTasks();
                break;
            case ACTIVE_TASKS:
                mTaskView.showNoActivateTask();
                break;
        }
    }

    private void processEmptyTask() {
        switch (mTaskCurrentType){
            case ALL_TASKS:
                mTaskView.showNoTasks();
                break;
            case COMPLETED_TASKS:
                mTaskView.showNoCompletedTasks();
                break;
            case ACTIVE_TASKS:
                mTaskView.showNoActivateTask();
                break;
        }

    }


    @Override
    public void setFilteringType(TaskFilterType type) {
          mTaskCurrentType = type;
    }

    @Override
    public void clearCompletedTasks() {

    }

    @Override
    public void addNewTask() {

        mTaskView.addNewTask();

    }

    @Override
    public void openTaskDetail(@NonNull Task requestedTask) {

    }

    @Override
    public void completeTask(@NonNull Task completedTask) {

    }

    @Override
    public void activeTask(@NonNull Task activeTask) {

    }

    @Override
    public void result(int resquestCode, int resultCode) {

        if(resquestCode == AddeditTaskActivity.REQUEST_ADD_TASK && resquestCode == Activity.RESULT_OK){

            mTaskView.showSaveTaskSucessfully();
        }

    }

    @Override
    public TaskFilterType getFilteringType() {
        return null;
    }
}
