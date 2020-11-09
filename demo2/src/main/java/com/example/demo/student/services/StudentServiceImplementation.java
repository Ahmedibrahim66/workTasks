package com.example.demo.student.services;

import com.example.demo.student.data.mysql.StudentRepositoryMySQL;
import com.example.demo.student.data.postgresSQL.StudentRepositoryPostgresSQL;
import com.example.demo.student.models.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImplementation implements StudentService {

    @Autowired
    StudentRepositoryMySQL repositorySQL;

    @Autowired
    StudentRepositoryPostgresSQL repositoryPostgresSQL;

    @Override
    public void addStudent(Students student) {

        repositoryPostgresSQL.save(student);
        repositorySQL.save(student);

    }

    @Override
    public List<Students> getStudentsSQL() {
        List<Students> studentsList = new ArrayList<>();
        repositorySQL.findAll().forEach(studentsList::add);
        return studentsList;
    }

    @Override
    public List<Students> getStudentsPostgres() {
        List<Students> studentsList = new ArrayList<>();
        repositoryPostgresSQL.findAll().forEach(studentsList::add);
        return studentsList;
    }


}
