package com.jieyi.googletodomvp.tasks;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jieyi.googletodomvp.R;
import com.jieyi.googletodomvp.utils.ActivityUtils;
import com.jieyi.googletodomvp.utils.Injection;

public class TaskActivity extends AppCompatActivity {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";
    TaskContract.Present mTaskPresent;

    private DrawerLayout mdrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mdrawerLayout = findViewById(R.id.drawer_layout);
        mdrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if(navigationView !=null) setUpDrawerLayout(navigationView);

        TaskFragment taskFragment = (TaskFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(taskFragment == null){
            taskFragment = TaskFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),taskFragment,R.id.contentFrame);
        }
        mTaskPresent = new TaskPresent(Injection.provideTasksRepository(getApplicationContext()),taskFragment);

        if(savedInstanceState !=null){
          TaskFilterType currentTaskType = (TaskFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
          mTaskPresent.setFilteringType(currentTaskType);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            mdrawerLayout.openDrawer(GravityCompat.START);
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putSerializable(CURRENT_FILTERING_KEY, mTaskPresent.getFilteringType());

        super.onSaveInstanceState(outState);

    }

    private void setUpDrawerLayout(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.menu_tasklist:
                        break;
                    case R.id.menu_statistics:
                        break;
                }
                item.setChecked(true);
                mdrawerLayout.closeDrawers();
                return true;
            }
        });




    }


}
