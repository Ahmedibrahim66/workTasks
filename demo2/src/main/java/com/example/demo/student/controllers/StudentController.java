package com.example.demo.student.controllers;

import com.example.demo.student.models.Students;
import com.example.demo.student.services.StudentServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentServiceImplementation testService;

    @PostMapping(value = "/students")
    public void addStudent(@RequestBody Students student) {
        testService.addStudent(student);
    }

    @GetMapping(value = "/studentsSQL")
    public List<Students> getStudentsSQL() {
        return testService.getStudentsSQL();
    }

    @GetMapping(value = "/studentsPostgres")
    public List<Students> getStudentsPostgres() {
        return testService.getStudentsPostgres();
    }


}
