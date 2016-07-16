package com.fadetoproductions.rvkn.todo;


import java.util.Date;

public class ToDoItem {

    String name;
    Date dueDate;

    public ToDoItem(String name, Date dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }

    public String getName() {
        return this.name;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setItemName(String name) {
        this.name = name;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}