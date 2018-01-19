package com.jieyi.googletodomvp.tasks;

import android.support.annotation.NonNull;

import com.jieyi.googletodomvp.BasePresent;
import com.jieyi.googletodomvp.BaseView;
import com.jieyi.googletodomvp.model.beans.Task;

import java.util.List;

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

public interface TaskContract {

    public interface View extends BaseView<Present>{

        void setShowLoadingProgress(boolean isShowLoading);
        void showTasks(List<Task> tasks);
        void showLoadingTaskError();
        void addNewTask();
        void clearCompletedTasks();
        void showTaskDetailUI(String taskId);
        void markTaskCompleted();
        void markTaskActive();
        void showSaveTaskSucessfully();
        void showNoTasks();
        void showNoCompletedTasks();
        boolean isActive();
        void showNoActivateTask();
        void showFilteringPopMenu();
        void showActiveFilterLabel();
        void showCompletedFilterLabel();
        void showAllFilterLabel();

    }

    public interface Present extends BasePresent{

        void loadTasks(boolean forceUpdate);
        void setFilteringType(TaskFilterType type);
        void clearCompletedTasks();
        void addNewTask();
        void openTaskDetail(@NonNull Task requestedTask);
        void completeTask(@NonNull Task completedTask);
        void activeTask(@NonNull Task activeTask);
        void result(int resquestCode,int resultCode);
        TaskFilterType getFilteringType();



    }

}
