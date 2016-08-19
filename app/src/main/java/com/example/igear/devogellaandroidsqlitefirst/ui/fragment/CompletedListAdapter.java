package com.example.igear.devogellaandroidsqlitefirst.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igear.devogellaandroidsqlitefirst.R;
import com.example.igear.devogellaandroidsqlitefirst.sqlite.CompletedItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IGear on 8/16/2016.
 */
public class CompletedListAdapter extends RecyclerView.Adapter<CompletedViewHolder> {
    private List<CompletedItem> completedItems;

    public CompletedListAdapter(List<CompletedItem> completedItems){
        this.completedItems = completedItems;
    }

    @Override
    public CompletedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.completed_item, parent, false);

        return new CompletedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CompletedViewHolder holder, int position) {
        CompletedItem completedItem = completedItems.get(position);
        holder.vTaskDesc.setText(completedItem.getTask());
        String date = getDate(completedItem.getCompleteDate(), "dd.MM.yyyy ', ' h:mm a");
        holder.vCompleteDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return completedItems.size();
    }

    public void addItem(CompletedItem item){
        completedItems.add(item);
    }

    public String getDate(long milliSeconds, String dateFormat){

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public void setCompletedList(List<CompletedItem> completedItems){
        this.completedItems = completedItems;
    }
}
