package com.example.igear.devogellaandroidsqlitefirst.ui.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.igear.devogellaandroidsqlitefirst.R;
import com.example.igear.devogellaandroidsqlitefirst.sqlite.ToDoItem;
import com.example.igear.devogellaandroidsqlitefirst.sqlite.ToDoListDataSource;

import java.util.List;

/**
 * Created by IGear on 8/11/2016.
 */
public class ToDoListFragment extends Fragment {
    private RelativeLayout _mainLayout;
    private ToDoAdapter _toDoAdapter;
    private ToDoListDataSource dataSource;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        _mainLayout = (RelativeLayout) inflater.inflate(R.layout.f_main, container, false);

        dataSource = new ToDoListDataSource(getActivity());
        dataSource.open();

        List<ToDoItem> toDoList = dataSource.getAllTasks();

        _toDoAdapter = new ToDoAdapter(toDoList);

        RecyclerView toDoListView = (RecyclerView) _mainLayout.findViewById(R.id.toDoList_recycler);
        toDoListView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        toDoListView.setLayoutManager(llm);
        toDoListView.setNestedScrollingEnabled(false);
        toDoListView.setAdapter(_toDoAdapter);

        return _mainLayout;
    }
    public void addToDoItem(String taskDesc){
        //save the new toDoItem to the database
        ToDoItem toDoItem = dataSource.createTask(taskDesc);
        _toDoAdapter.addItem(toDoItem);
        _toDoAdapter.notifyDataSetChanged();
    }

    public void deleteToDoItem(){
        if (_toDoAdapter.getItemCount() > 0){
            ToDoItem toDoItem = (ToDoItem) _toDoAdapter.getItem(0);
            dataSource.deleteTask(toDoItem);
            _toDoAdapter.removeItem(toDoItem);
            _toDoAdapter.notifyDataSetChanged();
        }
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
