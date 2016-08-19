package com.example.igear.devogellaandroidsqlitefirst.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.igear.devogellaandroidsqlitefirst.R;

/**
 * Created by IGear on 8/16/2016.
 */
public class CompletedViewHolder extends RecyclerView.ViewHolder{
    protected TextView vTaskDesc;
    protected TextView vCompleteDate;
    public final View mainView;

    public CompletedViewHolder(View itemView){
        super(itemView);
        vTaskDesc = (TextView) itemView.findViewById(R.id.task_desc);
        vCompleteDate = (TextView) itemView.findViewById(R.id.complete_date) ;
        mainView = itemView;
    }


}
