package com.example.demo.student.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class Students {

    @javax.persistence.Id
    private int Id;
    private String name;
    private int age;

    public Students(int id, String name, int age) {
        Id = id;
        this.name = name;
        this.age = age;
    }

    public Students() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
}
