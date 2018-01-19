package com.jieyi.googletodomvp.addedittask;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jieyi.googletodomvp.model.beans.Task;
import com.jieyi.googletodomvp.model.source.TasksDataSource;
import com.jieyi.googletodomvp.model.source.TasksRepository;

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

public class AddeditPresenter implements AddeditContract.Presenter,TasksDataSource.GetTaskCallback {

    private AddeditContract.View mAddeditView;
    private TasksRepository mTasksRepository;
    @NonNull
    private String mtaskId;
    private boolean misDataMissing;


    public AddeditPresenter(AddeditContract.View addeditView, TasksRepository tasksRepository,String staskId,boolean isDataMissing) {
        this.mAddeditView = checkNotNull(addeditView);
        this.mTasksRepository = checkNotNull(tasksRepository);
        mtaskId = staskId;
        misDataMissing = isDataMissing;

        mAddeditView.setPresent(this);
    }

    private boolean isNewTask(){
        return TextUtils.isEmpty(mtaskId);
    }

    @Override
    public void start() {

        if(!isNewTask() && misDataMissing){

            showTaskInfo();
        }

    }

    @Override
    public void showTaskInfo() {

        if(isNewTask()){
            throw new RuntimeException("调用showTaskInfo，但是taskId为空");
        }
        mTasksRepository.getTask(mtaskId,this);
    }

    @Override
    public void saveTask(String title, String description) {

        if(isNewTask()){
            createNewTask(title,description);
        }else {
           updateTask(title,description);
        }
    }

    private void updateTask(String title, String description) {

        mTasksRepository.saveTask(new Task(title,description,mtaskId));
        mAddeditView.showTaskLists();

    }

    private void createNewTask(String title, String description) {

        mTasksRepository.saveTask(new Task(title,description));
        mAddeditView.showTaskLists();
    }

    @Override
    public boolean isDataMissing() {
        return misDataMissing;
    }

    @Override
    public void onTaskLoaded(Task task) {
        mAddeditView.setTaskTitle(task.getTitle());
        mAddeditView.setContent(task.getDescription());
        misDataMissing = false;
    }

    @Override
    public void onDataNotAvailable() {
        if(mAddeditView.isactive()) mAddeditView.showEmptyTaskError();
    }
}
