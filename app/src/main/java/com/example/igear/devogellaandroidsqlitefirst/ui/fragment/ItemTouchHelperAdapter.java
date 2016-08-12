package com.example.igear.devogellaandroidsqlitefirst.ui.fragment;
/**
 * Created by IGear on 8/12/2016.
 */
public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
