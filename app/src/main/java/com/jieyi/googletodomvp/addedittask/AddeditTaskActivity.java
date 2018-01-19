package com.jieyi.googletodomvp.addedittask;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jieyi.googletodomvp.R;
import com.jieyi.googletodomvp.utils.ActivityUtils;
import com.jieyi.googletodomvp.utils.Injection;

public class AddeditTaskActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_TASK = 1;
    public static final String SHOULD_LOAD_DATA_FROM_REPO_KEY = "SHOULD_LOAD_DATA_FROM_REPO_KEY";

    private AddeditPresenter maddeditPresenter;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_task);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        AddeditFragment addeditFragment = (AddeditFragment) getSupportFragmentManager().findFragmentById(R.id.edit_container);
        String taskId = getIntent().getStringExtra(AddeditFragment.ARGUMENT_EDIT_TASK_ID);
        if(taskId !=null){
            mActionBar.setTitle("重新编辑");
        }else {
            mActionBar.setTitle("编辑");
        }

        if(addeditFragment == null){
            addeditFragment = AddeditFragment.newInstance();
            if(getIntent().hasExtra(AddeditFragment.ARGUMENT_EDIT_TASK_ID)){
                Bundle extra = new Bundle();
                extra.putString(AddeditFragment.ARGUMENT_EDIT_TASK_ID,taskId);
                addeditFragment.setArguments(extra);
            }

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),addeditFragment,R.id.edit_container);

        }

        boolean shouleLoadDataFromRepo = true;
        if (savedInstanceState != null) {
            // Data might not have loaded when the config change happen, so we saved the state.
            shouleLoadDataFromRepo = savedInstanceState.getBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY);
        }
        maddeditPresenter = new AddeditPresenter(addeditFragment, Injection.provideTasksRepository(getApplication()),taskId,shouleLoadDataFromRepo);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

      // outState.putBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY,maddeditPresenter.is);
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }
}
