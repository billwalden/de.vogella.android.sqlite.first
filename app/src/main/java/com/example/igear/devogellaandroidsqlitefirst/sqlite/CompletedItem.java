package com.example.igear.devogellaandroidsqlitefirst.sqlite;

/**
 * Created by IGear on 8/16/2016.
 */
public class CompletedItem implements Comparable<CompletedItem> {
    private long id;
    private String comment;
    private long completeDate;

    public long getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(long completeDate) {
        this.completeDate = completeDate;
    }


    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getTask(){
        return comment;
    }

    public void setTask(String comment){
        this.comment = comment;
    }

    public int compareTo(CompletedItem item)
    {
        return (int)(completeDate - item.completeDate);
    }

    @Override
    public String toString(){
        return comment;
    }
}

