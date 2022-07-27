package com.example.restapitest;

public class TodoModel {
        private Integer id;

        private String title;

    public TodoModel(int id, String title) {
        this.id = id;
        this.title = title;
    }
    public TodoModel(String title) {
        this.title = title;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
