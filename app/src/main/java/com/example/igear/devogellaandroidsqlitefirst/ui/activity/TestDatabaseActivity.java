package com.example.igear.devogellaandroidsqlitefirst.ui.activity;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.igear.devogellaandroidsqlitefirst.R;
import com.example.igear.devogellaandroidsqlitefirst.ui.dialog.AddItemDialog;
import com.example.igear.devogellaandroidsqlitefirst.ui.fragment.ToDoListFragment;


public class TestDatabaseActivity extends AppCompatActivity {
    private ToDoListFragment _mainFragment = new ToDoListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.am_content, _mainFragment).commit();
    }
    //Will be called via the onClick attribute
    //of the buttons in main.xml
    public void onClick(View view) {
        switch ((view.getId())){
            case R.id.delete:
                _mainFragment.deleteToDoItem();
                break;
            case R.id.fab_button:
                AddItemDialog newFragment = (AddItemDialog) AddItemDialog.getDialogInstance();
                newFragment.show(getFragmentManager(), "dialog");
                break;
        }
    }
   /* private void showDialog() {
        DialogFragment newFragment = (DialogFragment) ToDoListFragment.instantiate(this, "Add Task");
        newFragment.show(getSupportFragmentManager(), "dialog");
    }*/

    public void doPositiveClick(String taskDesc) {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Positive click!");
        _mainFragment.addToDoItem(taskDesc);
    }

    public void doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!");
    }
}
