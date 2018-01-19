package com.jieyi.googletodomvp.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.jieyi.googletodomvp.R;
import com.jieyi.googletodomvp.addedittask.AddeditTaskActivity;
import com.jieyi.googletodomvp.model.beans.Task;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class TaskFragment extends Fragment implements TaskContract.View{
    private static final String ARG_PARAM1 = "param1";

    private TaskContract.Present mPresenter;

    private ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout tasksViewLayout;
    private View noTaskViewLayout;
    private ListView mlistView;
    private TextView noTasksMain;
    private TextView noTaskAddView;
    private TextView taskLable;
    private View mRootview;
    private TaskAdapter madapter;



    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       madapter = new TaskAdapter(new ArrayList<Task>(),itemListener);
    }

    private TaskItemListener itemListener = new TaskItemListener() {
        @Override
        public void onTaskClick(Task clickedTask) {

        }

        @Override
        public void onCompletedTaskClick(Task completedTask) {

        }

        @Override
        public void onActivateTaskClick(Task activateTask) {

        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

       inflater.inflate(R.menu.refresh_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_clearcomplted:
                mPresenter.clearCompletedTasks();
                break;
            case R.id.menu_refresh:
                mPresenter.loadTasks(true);
                break;
            case R.id.menu_filter:
             showFlilterPopuMenu();
                break;


        }

        return true;
    }

    private void showFlilterPopuMenu() {

        PopupMenu popupMenu = new PopupMenu(getContext(),getActivity().findViewById(R.id.menu_filter));

        popupMenu.getMenuInflater().inflate(R.menu.filter_task,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.menu_taskAll:
                        mPresenter.setFilteringType(TaskFilterType.ALL_TASKS);
                        break;
                    case R.id.menu_Complete:
                        mPresenter.setFilteringType(TaskFilterType.COMPLETED_TASKS);
                    case R.id.menu_Active :
                        mPresenter.setFilteringType(TaskFilterType.ACTIVE_TASKS);
                        break;
                    default:
                        mPresenter.setFilteringType(TaskFilterType.ALL_TASKS);
                }

                mPresenter.loadTasks(false);

                return true;
            }
        });

          popupMenu.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootview = inflater.inflate(R.layout.fragment_task,container,false);

        initView();//初始化view
        setHasOptionsMenu(true);
        return mRootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("zyh","TaskFrg onResume is going");
        mPresenter.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.result(requestCode,resultCode);
    }

    private void initView() {
        mSwipeRefreshLayout = mRootview.findViewById(R.id.refresh_layout_task);
        mlistView = mRootview.findViewById(R.id.lv_tasks);
        tasksViewLayout = mRootview.findViewById(R.id.task_LL);
        noTaskViewLayout = mRootview.findViewById(R.id.tasksNo);
        noTasksMain = mRootview.findViewById(R.id.noTasksMain);
        noTaskAddView = mRootview.findViewById(R.id.noTasksAdd);
        mlistView.setAdapter(madapter);

       mSwipeRefreshLayout.setScrollUpChild(mlistView);
       mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               mPresenter.loadTasks(true);
           }
       });

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.addNewTask();
            }
        });

    }



    @Override
    public void setPresent(TaskContract.Present present) {
              mPresenter = checkNotNull(present);
    }

    @Override
    public void setShowLoadingProgress(final boolean isShowLoading) {
       mSwipeRefreshLayout.post(new Runnable() {
           @Override
           public void run() {
               mSwipeRefreshLayout.setEnabled(isShowLoading);
           }
       });
    }

    @Override
    public void showTasks(List<Task> tasks) {
        Log.e("zyh","tasks.size---> "+tasks.size());
        madapter.setListDatas(tasks);
        tasksViewLayout.setVisibility(View.VISIBLE);
        noTaskViewLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingTaskError() {

    }


    @Override
    public void addNewTask() {

        Intent it = new Intent(getActivity(), AddeditTaskActivity.class);
        startActivityForResult(it,AddeditTaskActivity.REQUEST_ADD_TASK);
    }

    @Override
    public void clearCompletedTasks() {

    }

    @Override
    public void showTaskDetailUI(String taskId) {

    }

    @Override
    public void markTaskCompleted() {

    }

    @Override
    public void markTaskActive() {

    }

    @Override
    public void showSaveTaskSucessfully() {
         showMessage("事务已保存");
    }


    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }
    private void showNoTaskView(String strTips,boolean isShowAddView){

        tasksViewLayout.setVisibility(View.GONE);
        noTaskViewLayout.setVisibility(View.VISIBLE);

        noTasksMain.setText(strTips);
        noTaskAddView.setVisibility(isShowAddView?View.VISIBLE:View.GONE);

    }
    @Override
    public void showNoTasks() {

    }

    @Override
    public void showNoCompletedTasks() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showNoActivateTask() {

    }

    @Override
    public void showFilteringPopMenu() {

    }

    @Override
    public void showActiveFilterLabel() {
       taskLable.setText("代办事务");
    }

    @Override
    public void showCompletedFilterLabel() {
      taskLable.setText("已完成事务");
    }

    @Override
    public void showAllFilterLabel() {
       taskLable.setText("所有事务");
    }

    public class TaskAdapter extends BaseAdapter{

       private List<Task> mdatas;
       private TaskItemListener mtaskItemListener;

        public TaskAdapter(List<Task> mdatas, TaskItemListener mtaskItemListener) {
            this.mdatas = mdatas;
            this.mtaskItemListener = mtaskItemListener;
        }


        public void setListDatas(List<Task> tasks){
            mdatas.clear();
            mdatas.addAll(tasks);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mdatas == null?0:mdatas.size();
        }

        @Override
        public Task getItem(int i) {
            return mdatas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View containerView, ViewGroup viewGroup) {

            ViewHolder holder = null;

            if(containerView == null){
                holder = new ViewHolder();
               LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                containerView = inflater.inflate(R.layout.task_item,viewGroup,false);
                holder.title = containerView.findViewById(R.id.title);
                holder.checkBox = containerView.findViewById(R.id.complete);
                containerView.setTag(holder);
            }else {

                holder = (ViewHolder) containerView.getTag();

            }

            final Task task = getItem(position);

            holder.title.setText(task.getTitle());
            holder.checkBox.setChecked(task.isCompleted());
            if (task.isCompleted()) {
                containerView.setBackgroundDrawable(viewGroup.getContext()
                        .getResources().getDrawable(R.drawable.list_completed_touch_feedback));
            } else {
                containerView.setBackgroundDrawable(viewGroup.getContext()
                        .getResources().getDrawable(R.drawable.touch_feedback));
            }

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!task.isCompleted()){
                        mtaskItemListener.onCompletedTaskClick(task);
                    }else {
                        mtaskItemListener.onActivateTaskClick(task);
                    }
                }
            });

            mRootview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mtaskItemListener.onTaskClick(task);
                }
            });

            return containerView;
        }

        class ViewHolder{

            TextView title;
            CheckBox checkBox;
        }

    }

    public interface TaskItemListener{

        void onTaskClick(Task clickedTask);
        void onCompletedTaskClick(Task completedTask);
        void onActivateTaskClick(Task activateTask);

    }

}
