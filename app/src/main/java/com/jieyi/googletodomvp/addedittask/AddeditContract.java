package com.jieyi.googletodomvp.addedittask;

import android.widget.BaseAdapter;

import com.jieyi.googletodomvp.BasePresent;
import com.jieyi.googletodomvp.BaseView;

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

public interface AddeditContract {

    public interface View extends BaseView<Presenter>{

        void showEmptyTaskError();
        void showTaskLists();
        void setTaskTitle(String title);
        void setContent(String description);
        boolean isactive();
    }

    public interface Presenter extends BasePresent{
        void showTaskInfo();
        void saveTask(String title, String description);
        boolean isDataMissing();
    }

}
