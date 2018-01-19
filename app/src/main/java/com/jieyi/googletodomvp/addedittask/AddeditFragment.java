package com.jieyi.googletodomvp.addedittask;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jieyi.googletodomvp.R;


public class AddeditFragment extends Fragment implements AddeditContract.View{

    public static final String ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID";
    private EditText et_title;
    private EditText et_description;
    private FloatingActionButton fab;
    private View mrootView;
    private AddeditContract.Presenter mPresenter;

    public static AddeditFragment newInstance() {
        return new AddeditFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mrootView = inflater.inflate(R.layout.fragment_addedit,container,false);
       et_title = mrootView.findViewById(R.id.et_title);
       et_description = mrootView.findViewById(R.id.et_description);
       fab = getActivity().findViewById(R.id.fab_edittask_done);
       fab.setImageResource(R.drawable.ic_done);
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mPresenter.saveTask(et_title.getText().toString(),et_description.getText().toString());
           }
       });

        return mrootView;
    }

    @Override
    public void onResume() {
        super.onResume();
      mPresenter.start();
    }

    @Override
    public void setPresent(AddeditContract.Presenter present) {

        mPresenter = present;
    }

    @Override
    public void showEmptyTaskError() {
        Snackbar.make(et_title,"内容不能为空",Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void showTaskLists() {
        getActivity().setResult(Activity.RESULT_OK);
       getActivity().finish();
    }

    @Override
    public void setTaskTitle(String title) {
       et_title.setText(title);
    }

    @Override
    public void setContent(String description) {
          et_description.setText(description);
    }

    @Override
    public boolean isactive() {
        return isAdded();
    }
}
