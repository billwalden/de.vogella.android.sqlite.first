package com.example.igear.devogellaandroidsqlitefirst.ui.fragment;

import android.app.Activity;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.igear.devogellaandroidsqlitefirst.R;
import com.example.igear.devogellaandroidsqlitefirst.sqlite.ToDoItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by IGear on 8/11/2016.
 */
public class ToDoAdapter
        extends RecyclerView.Adapter<ToDoViewHolder>
        implements ItemTouchHelperAdapter{

    private List<ToDoItem> toDoList;
    private final OnStartDragListener mDragStartListener;
    private Integer lastPriority;

    public ToDoAdapter(List<ToDoItem> toDoList, OnStartDragListener startDragListener){
        this.toDoList = toDoList;
        if(!toDoList.isEmpty()){
            lastPriority = new Integer(toDoList.get(toDoList.size() - 1).getPriority());
        }
        lastPriority = toDoList.get(toDoList.size() - 1).getPriority();
        mDragStartListener = startDragListener;
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_layout, parent, false);

        return new ToDoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ToDoViewHolder holder, int position) {
        ToDoItem toDoItem = toDoList.get(position);
        holder.vTask.setText(toDoItem.getTask());
        holder.vArrows.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public ToDoItem getItem(int position){
        return toDoList.get(position);
    }

    public void addItem(ToDoItem item){
        toDoList.add(item);
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(toDoList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        ToDoItem draggedItem = toDoList.get(toPosition);
        mDragStartListener.onItemMoved(draggedItem);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        toDoList.remove(position);
        notifyItemRemoved(position);
        mDragStartListener.onDismissed();
    }

    public Integer getLastPriority(){
        return lastPriority;
    }
}
