package com.example.makkerzsombor_javafxrestclientdolgoza;

import com.google.gson.annotations.Expose;

public class Person {
    private int id;

    @Expose
    private String name;
    private int age, height;
    private boolean married;

    public Person(int id, String name, int age, boolean married, int height) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.married = married;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
