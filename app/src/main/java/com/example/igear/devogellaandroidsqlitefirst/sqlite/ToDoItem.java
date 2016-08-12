package com.example.igear.devogellaandroidsqlitefirst.sqlite;

/**
 * Created by IGear on 8/10/2016.
 */
public class ToDoItem implements Comparable<ToDoItem>{
    private long id;
    private int priority;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int compareTo(ToDoItem item)
    {
        return(priority - item.priority);
    }

    @Override
    public String toString(){
        return comment;
    }
}
