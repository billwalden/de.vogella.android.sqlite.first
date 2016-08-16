package com.example.igear.devogellaandroidsqlitefirst.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.igear.devogellaandroidsqlitefirst.R;
import com.example.igear.devogellaandroidsqlitefirst.sqlite.CompletedItem;
import com.example.igear.devogellaandroidsqlitefirst.sqlite.ToDoItem;
import com.example.igear.devogellaandroidsqlitefirst.sqlite.ToDoListDataSource;

import java.util.List;

/**
 * Created by IGear on 8/15/2016.
 */

public class CompletedListFragment extends Fragment  {
    private RelativeLayout _mainLayout;
    private ToDoListDataSource dataSource;
    private CompletedListAdapter _completedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _mainLayout = (RelativeLayout) inflater.inflate(R.layout.f_completed, container, false);

        dataSource = new ToDoListDataSource(getActivity());
        dataSource.open();
        List<CompletedItem> completedItemList = dataSource.getCompletedTasks();

        _completedAdapter = new CompletedListAdapter(completedItemList);

        RecyclerView toDoListView = (RecyclerView) _mainLayout.findViewById(R.id.toDoList_recycler);
        toDoListView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        toDoListView.setLayoutManager(llm);
        toDoListView.setNestedScrollingEnabled(false);
        toDoListView.setAdapter(_completedAdapter);

        return _mainLayout;
    }
    public void updateCompletedList(){
        _completedAdapter.setCompletedList(dataSource.getCompletedTasks());
        _completedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        dataSource.close();
        super.onPause();
    }
}
