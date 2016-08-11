package com.example.igear.devogellaandroidsqlitefirst.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.igear.devogellaandroidsqlitefirst.R;

import org.w3c.dom.Text;

/**
 * Created by IGear on 8/11/2016.
 */
public class ToDoViewHolder extends RecyclerView.ViewHolder {
    protected TextView vTask;
    public ToDoViewHolder(View itemView) {
        super(itemView);
        vTask = (TextView) itemView.findViewById(R.id.task);
    }
}
