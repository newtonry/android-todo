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
    public String priority;

    public Todo() {
        super();
    }

    public Todo(String name, Date dueDate, String priority) {
        super();
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public static List<Todo> getAll() {
        return new Select()
                .from(Todo.class)
                .execute();
    }
}