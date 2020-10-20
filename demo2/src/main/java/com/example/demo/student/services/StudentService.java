package com.example.demo.student.services;

import com.example.demo.student.models.Students;

import java.util.List;

public interface StudentService {

    public void addStudent(Students student);
    public List<Students> getStudentsSQL();
    public List<Students> getStudentsPostgres();


}
