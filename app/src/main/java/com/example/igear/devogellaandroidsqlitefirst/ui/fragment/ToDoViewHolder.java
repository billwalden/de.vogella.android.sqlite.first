package com.example.igear.devogellaandroidsqlitefirst.ui.fragment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.igear.devogellaandroidsqlitefirst.R;

import org.w3c.dom.Text;

import static com.example.igear.devogellaandroidsqlitefirst.R.color.toDoItemBackground;

/**
 * Created by IGear on 8/11/2016.
 */
public class ToDoViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{
    protected TextView vTask;
    protected ImageView vArrows;
    public final View mainView;
    public ToDoViewHolder(View itemView) {
        super(itemView);
        vTask = (TextView) itemView.findViewById(R.id.task);
        vArrows = (ImageView) itemView.findViewById(R.id.arrows) ;
        mainView = itemView;
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundResource(R.color.toDoItemBackground);
    }
}
