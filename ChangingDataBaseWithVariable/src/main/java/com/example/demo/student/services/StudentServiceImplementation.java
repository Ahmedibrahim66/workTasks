package com.example.demo.student.services;

import com.example.demo.student.data.StudentRepository;
import com.example.demo.student.models.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImplementation implements StudentService {

    @Autowired
    StudentRepository repository;

    @Override
    public void addStudent(Students student) {

        repository.save(student);

    }

    @Override
    public List<Students> getStudents() {
        List<Students> studentsList = new ArrayList<>();
        repository.findAll().forEach(studentsList::add);
        return studentsList;
    }




}
