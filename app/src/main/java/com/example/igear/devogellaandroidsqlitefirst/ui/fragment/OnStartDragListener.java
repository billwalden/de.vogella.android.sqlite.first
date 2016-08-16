package com.example.igear.devogellaandroidsqlitefirst.ui.fragment;

import android.support.v7.widget.RecyclerView;

import com.example.igear.devogellaandroidsqlitefirst.sqlite.ToDoItem;

/**
 * Created by IGear on 8/12/2016.
 */
public interface OnStartDragListener {
    void onStartDrag(RecyclerView.ViewHolder viewHolder);

    void onItemMoved(ToDoItem itemDragged);

    void onDismissed(int position);

    void addCompletedItem(int position);
}
