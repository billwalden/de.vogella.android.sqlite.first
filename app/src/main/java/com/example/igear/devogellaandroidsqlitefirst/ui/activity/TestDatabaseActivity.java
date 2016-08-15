package com.example.igear.devogellaandroidsqlitefirst.ui.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.igear.devogellaandroidsqlitefirst.R;
import com.example.igear.devogellaandroidsqlitefirst.ui.dialog.AddItemDialog;
import com.example.igear.devogellaandroidsqlitefirst.ui.fragment.CompletedListFragment;
import com.example.igear.devogellaandroidsqlitefirst.ui.fragment.ToDoListFragment;

import java.util.ArrayList;
import java.util.List;


public class TestDatabaseActivity extends AppCompatActivity {
    private ToDoListFragment _mainFragment = new ToDoListFragment();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

       // FragmentManager fm = getSupportFragmentManager();
        //fm.beginTransaction().replace(R.id.am_content, _mainFragment).commit();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ToDoListFragment(), "To Do");
        adapter.addFragment(new CompletedListFragment(), "Completed");
        viewPager.setAdapter(adapter);
    }
    //Will be called via the onClick attribute
    //of the buttons in main.xml
    public void onClick(View view) {
        switch ((view.getId())){
            case R.id.fab_button:
                AddItemDialog newFragment = AddItemDialog.getDialogInstance();
                newFragment.show(getFragmentManager(), "dialog");
                break;
        }
    }

    public void doPositiveClick(String taskDesc) {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Positive click!");
        _mainFragment.addToDoItem(taskDesc);
    }

    public void doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!");
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}

