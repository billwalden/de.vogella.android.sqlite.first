package com.example.igear.devogellaandroidsqlitefirst.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igear.devogellaandroidsqlitefirst.R;
import com.example.igear.devogellaandroidsqlitefirst.sqlite.ToDoItem;

import java.util.List;

/**
 * Created by IGear on 8/11/2016.
 */
public class ToDoAdapter extends RecyclerView.Adapter<ToDoViewHolder> {

    private List<ToDoItem> toDoList;

    public ToDoAdapter(List<ToDoItem> toDoList){
        this.toDoList = toDoList;
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_layout, parent, false);

        return new ToDoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        ToDoItem toDoItem = toDoList.get(position);
        holder.vTask.setText(toDoItem.getTask());
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

    public void removeItem(ToDoItem item){
        toDoList.remove(item);
    }
}
