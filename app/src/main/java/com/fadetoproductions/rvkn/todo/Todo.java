package com.fadetoproductions.rvkn.todo;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;


@Table(name = "Todos")
public class Todo extends Model {

    @Column(name = "Name")

    public String name;
    @Column(name = "DueDate")

    public Date dueDate;
    @Column(name = "Priority")
    public Integer priority;


    public Todo() {
        super();
    }

    public static List<Todo> getAll() {
        // This is how you execute a query
        return new Select()
                .from(Todo.class)
                .execute();
    }


}