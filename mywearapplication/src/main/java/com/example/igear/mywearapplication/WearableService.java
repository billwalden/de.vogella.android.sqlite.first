package com.example.igear.mywearapplication;

import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by IGear on 8/19/2016.
 */
public class WearableService extends WearableListenerService {
private final String TAG = "WearableService";

    @Override
    public void onCreate() {
       Log.d(TAG, "WearableService was created");
    }


    @Override
    public void onDataChanged(DataEventBuffer dataEvents){
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
}
