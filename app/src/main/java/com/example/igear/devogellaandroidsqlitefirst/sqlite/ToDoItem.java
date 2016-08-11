package com.example.igear.devogellaandroidsqlitefirst.sqlite;

/**
 * Created by IGear on 8/10/2016.
 */
public class ToDoItem {
    private long id;
    private String comment;

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

    @Override
    public String toString(){
        return comment;
    }
}
