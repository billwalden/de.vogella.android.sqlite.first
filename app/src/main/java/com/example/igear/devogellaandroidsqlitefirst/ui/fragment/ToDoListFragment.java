package com.example.igear.devogellaandroidsqlitefirst.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.igear.devogellaandroidsqlitefirst.R;
import com.example.igear.devogellaandroidsqlitefirst.sqlite.ToDoItem;
import com.example.igear.devogellaandroidsqlitefirst.sqlite.ToDoListDataSource;
import com.example.igear.devogellaandroidsqlitefirst.ui.activity.TestDatabaseActivity;

import java.util.List;

/**
 * Created by IGear on 8/11/2016.
 */
public class ToDoListFragment extends Fragment implements OnStartDragListener {
    private RelativeLayout _mainLayout;
    private ToDoAdapter _toDoAdapter;
    private ToDoListDataSource dataSource;
    private ItemTouchHelper mItemTouchHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        _mainLayout = (RelativeLayout) inflater.inflate(R.layout.f_main, container, false);

        dataSource = new ToDoListDataSource(getActivity());
        dataSource.open();
        //upgradeDB();
        List<ToDoItem> toDoList = dataSource.getAllTasks();

        _toDoAdapter = new ToDoAdapter(toDoList, this);

        RecyclerView toDoListView = (RecyclerView) _mainLayout.findViewById(R.id.toDoList_recycler);
        toDoListView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        toDoListView.setLayoutManager(llm);
        toDoListView.setNestedScrollingEnabled(false);
        toDoListView.setAdapter(_toDoAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(_toDoAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(toDoListView);
        return _mainLayout;
    }

    public void addToDoItem(String taskDesc){
        //save the new toDoItem to the database
        if(_toDoAdapter.getLastPriority() == null){
            _toDoAdapter.setLastPriority(0);
        }
        else{
            _toDoAdapter.setLastPriority(_toDoAdapter.getLastPriority().intValue() + 1);
        }
        ToDoItem toDoItem = dataSource.createTask(taskDesc, _toDoAdapter.getLastPriority());
        _toDoAdapter.addItem(toDoItem);
        _toDoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        updateDBOrder();
        dataSource.close();
        super.onPause();
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }


    @Override
    public void onItemMoved(ToDoItem itemDragged) {
       // dataSource.updateTask(itemDragged);
    }

    @Override
    public void onDismissed(int position) {
        if (_toDoAdapter.getItemCount() > 0){
            ToDoItem toDoItem = _toDoAdapter.getItem(position);
            dataSource.deleteTask(toDoItem);
        }
    }

    public void addCompletedItem(int position){
        dataSource.addTaskToCompleted(_toDoAdapter.getItem(position));
        ((TestDatabaseActivity) getActivity()).updateCompletedTasks();
    }

    private void updateDBOrder(){
        dataSource.updateDatabaseOrder(_toDoAdapter.getToDoList());
    }
    private void upgradeDB(){
        dataSource.upgradeDatabase();
    }
}
