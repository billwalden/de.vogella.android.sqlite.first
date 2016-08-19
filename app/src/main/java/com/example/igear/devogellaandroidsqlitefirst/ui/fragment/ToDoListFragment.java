package com.example.igear.devogellaandroidsqlitefirst.ui.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.igear.devogellaandroidsqlitefirst.R;
import com.example.igear.devogellaandroidsqlitefirst.sqlite.ToDoItem;
import com.example.igear.devogellaandroidsqlitefirst.sqlite.ToDoListDataSource;
import com.example.igear.devogellaandroidsqlitefirst.ui.activity.TestDatabaseActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IGear on 8/11/2016.
 */
public class ToDoListFragment extends Fragment implements OnStartDragListener,  DataApi.DataListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private RelativeLayout _mainLayout;
    private ToDoAdapter _toDoAdapter;
    private ToDoListDataSource dataSource;
    private ItemTouchHelper mItemTouchHelper;
    private GoogleApiClient mGoogleApiClient;
    private final String TAG = "ToDoListFragment";



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

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        ArrayList<String> sList = new ArrayList<>();
        for (ToDoItem i:
             toDoList) {
            sList.add(i.getTask());
        }
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/toDoList");
        putDataMapReq.getDataMap().putLong("Time", System.currentTimeMillis());
        putDataMapReq.getDataMap().putStringArrayList("toDoList", sList);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();
        PendingResult<DataApi.DataItemResult> pendingResult =
                Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(final DataApi.DataItemResult result) {
                if(result.getStatus().isSuccess()) {
                    Log.d(TAG, "Data item set: " + result.getDataItem().getUri());
                }
            }
        });
        Wearable.DataApi.deleteDataItems(mGoogleApiClient, putDataReq.getUri());



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
        mGoogleApiClient.connect();
        List<ToDoItem> toDoList = _toDoAdapter.getToDoList();

        ArrayList<String> tasksList = new ArrayList<>();

        for (ToDoItem i:
                toDoList) {
            tasksList.add(i.getTask());
        }

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/toDoList");
        putDataMapReq.getDataMap().putLong("NewTime", System.currentTimeMillis());
        putDataMapReq.getDataMap().putStringArrayList("toDoList", tasksList);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();
        PendingResult<DataApi.DataItemResult> pendingResult =
                Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(final DataApi.DataItemResult result) {
                if(result.getStatus().isSuccess()) {
                    Log.d(TAG, "Data item set: " + result.getDataItem().getUri());
                }
            }
        });
        Wearable.DataApi.deleteDataItems(mGoogleApiClient, putDataReq.getUri());
        super.onResume();
    }

    @Override
    public void onPause() {
        updateDBOrder();
        dataSource.close();
        Wearable.DataApi.removeListener(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.DataApi.addListener(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for(DataEvent dataEvent: dataEvents){
            if(dataEvent.getType() == DataEvent.TYPE_CHANGED){
                DataMap dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
                String path = dataEvent.getDataItem().getUri().getPath();
                if(path.equals("/toDoList")){
                    dataMap.getStringArrayList("toDoList");
                    Log.d(TAG, "Successfully retrieved the ToDo List");
                }
            }
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
